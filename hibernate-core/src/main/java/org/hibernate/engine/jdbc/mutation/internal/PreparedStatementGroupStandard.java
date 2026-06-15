/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import java.sql.PreparedStatement;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.hibernate.engine.jdbc.mutation.group.PreparedStatementDetails;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.jdbc.spi.MutationStatementPreparer;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.values.GeneratedValuesMutationDelegate;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.hibernate.sql.model.TableMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A group of {@link PreparedStatementDetails} references related to multi-table
 * entity mappings.  The statements are keyed by each table-names.
 *
 * @author Steve Ebersole
 */
public class PreparedStatementGroupStandard extends AbstractPreparedStatementGroup {
	private final MutationType mutationType;
	private final MutationTarget<?,?> mutationTarget;
	private final List<PreparableMutationOperation> jdbcMutations;

	private final SortedMap<String, PreparedStatementDetails> statementMap;


	public PreparedStatementGroupStandard(
			MutationType mutationType,
			MutationTarget<?,?> mutationTarget,
			GeneratedValuesMutationDelegate generatedValuesDelegate,
			List<PreparableMutationOperation> jdbcMutations,
			SharedSessionContractImplementor session) {
		super( session );
		this.mutationType = mutationType;
		this.mutationTarget = mutationTarget;
		this.jdbcMutations = jdbcMutations;


		this.statementMap = createStatementDetailsMap( jdbcMutations, mutationType, generatedValuesDelegate, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfStatements() {
		return jdbcMutations.size();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getNumberOfActiveStatements() {
		int count = 0;
		for ( var entry : statementMap.entrySet() ) {
			if ( entry.getValue().getStatement() != null ) {
				count++;
			}
		}
		return count;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails getSingleStatementDetails() {
		throw new IllegalStateException(
				String.format(
						Locale.ROOT,
						"Statement group contained more than one statement - %s : %s",
						mutationType.name(),
						mutationTarget.getNavigableRole().getFullPath()
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachStatement(BiConsumer<String, PreparedStatementDetails> action) {
		statementMap.forEach( action );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails getPreparedStatementDetails(String tableName) {
		return statementMap.get( tableName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails resolvePreparedStatementDetails(String tableName) {
		return statementMap.get( tableName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasMatching(Predicate<PreparedStatementDetails> filter) {
		for ( var entry : statementMap.entrySet() ) {
			if ( filter.test( entry.getValue() ) ) {
				return true;
			}
		}
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static PreparedStatementDetails createPreparedStatementDetails(
			PreparableMutationOperation jdbcMutation,
			GeneratedValuesMutationDelegate generatedValuesDelegate,
			SharedSessionContractImplementor session) {
		final JdbcCoordinator jdbcCoordinator = session.getJdbcCoordinator();
		final MutationStatementPreparer statementPreparer = jdbcCoordinator.getMutationStatementPreparer();

		final TableMapping tableDetails = jdbcMutation.getTableDetails();

		final Supplier<PreparedStatement> jdbcStatementCreator;
		if ( tableDetails.isIdentifierTable() && generatedValuesDelegate != null ) {
			jdbcStatementCreator = () -> generatedValuesDelegate.prepareStatement(
					jdbcMutation.getSqlString(),
					session
			);
		}
		else {
			jdbcStatementCreator = () -> statementPreparer.prepareStatement(
					jdbcMutation.getSqlString(),
					jdbcMutation.isCallable()
			);
		}

		return new PreparedStatementDetailsStandard(
				jdbcMutation,
				jdbcMutation.getSqlString(),
				jdbcStatementCreator,
				jdbcMutation.getExpectation(),
				session.getJdbcServices()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void release() {
		statementMap.forEach( (tableName, statementDetails) -> {
			release( statementDetails );
		} );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static SortedMap<String, PreparedStatementDetails> createStatementDetailsMap(
			List<PreparableMutationOperation> jdbcMutations,
			MutationType mutationType,
			GeneratedValuesMutationDelegate generatedValuesDelegate,
			SharedSessionContractImplementor session) {
		final Comparator<String> comparator;

		if ( mutationType == MutationType.DELETE ) {
			// reverse order
			comparator = Comparator.comparingInt( (tableName) -> {
				final TableMapping tableMapping = locateTableMapping( jdbcMutations, tableName );
				if ( tableMapping == null ) {
					return -1;
				}
				return jdbcMutations.size() - tableMapping.relativePosition();
			} );
		}
		else {
			comparator = Comparator.comparingInt( (tableName) -> {
				final TableMapping tableMapping = locateTableMapping( jdbcMutations, tableName );
				if ( tableMapping == null ) {
					return -1;
				}
				return tableMapping.relativePosition();
			} );
		}

		final TreeMap<String, PreparedStatementDetails> map = new TreeMap<>( comparator );

		for ( final PreparableMutationOperation jdbcMutation : jdbcMutations ) {
			map.put(
					jdbcMutation.getTableDetails().getTableName(),
					createPreparedStatementDetails( jdbcMutation, generatedValuesDelegate, session )
			);
		}

		return map;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static TableMapping locateTableMapping(List<PreparableMutationOperation> jdbcMutations, String name) {
		for ( final PreparableMutationOperation jdbcMutation : jdbcMutations ) {
			final TableMapping tableMapping = jdbcMutation.getTableDetails();
			if ( tableMapping.getTableName().equals( name ) ) {
				return tableMapping;
			}
		}
		return null;
	}

}
