/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.mapping.Column;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.mapping.BasicValue;
import org.hibernate.mapping.Stateful;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.LegacyAuxiliaryMutationSupport;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.TemporalMapping;
import org.hibernate.persister.entity.EntityNameUse;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAliasBaseGenerator;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlExpressionResolver;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.SelfRenderingSqlFragmentExpression;
import org.hibernate.sql.ast.tree.from.LazyTableGroup;
import org.hibernate.sql.ast.tree.from.NamedTableReference;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.ast.tree.predicate.ComparisonPredicate;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.hibernate.sql.ast.tree.predicate.NullnessPredicate;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.ast.ColumnWriteFragment;
import org.hibernate.sql.exec.internal.TemporalJdbcParameter;
import org.hibernate.sql.model.ast.builder.MutationGroupBuilder;
import org.hibernate.sql.model.ast.builder.TableInsertBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static org.hibernate.boot.model.internal.TemporalHelper.ROW_END;
import static org.hibernate.boot.model.internal.TemporalHelper.ROW_START;
import static org.hibernate.query.sqm.ComparisonOperator.GREATER_THAN;
import static org.hibernate.query.sqm.ComparisonOperator.LESS_THAN_OR_EQUAL;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Temporal mapping implementation.
 *
 * @author Gavin King
 *
 * @since 7.4
 */
public class TemporalMappingImpl implements TemporalMapping, LegacyAuxiliaryMutationSupport {
	private final String tableName;
	private final SelectableMapping startingColumnMapping;
	private final SelectableMapping endingColumnMapping;
	private final JdbcMapping jdbcMapping;
	private final String currentTimestampFunctionName;
	private final Expression currentTimestampExpression;
	private final TemporalTableStrategy temporalTableStrategy;

	public TemporalMappingImpl(
			Stateful bootMapping,
			String tableName,
			MappingModelCreationProcess creationProcess) {
		this.tableName = tableName;

		final var creationContext = creationProcess.getCreationContext();
		temporalTableStrategy =
				creationContext.getSessionFactoryOptions()
						.getTemporalTableStrategy();

		final var startingColumn = bootMapping.getAuxiliaryColumn( ROW_START );
		final var endingColumn = bootMapping.getAuxiliaryColumn( ROW_END );
		final var startingValue = (BasicValue) startingColumn.getValue();
		final var endingValue = (BasicValue) endingColumn.getValue();

		final var startingResolution = startingValue.resolve();
		final var endingResolution = endingValue.resolve();

		jdbcMapping = startingResolution.getJdbcMapping();
		if ( jdbcMapping != endingResolution.getJdbcMapping() ) {
			throw new IllegalStateException( "Temporal starting and ending columns must use the same JDBC mapping" );
		}

		startingColumnMapping = startingColumnMapping( tableName, startingColumn, creationContext );
		endingColumnMapping = endingColumnMapping( tableName, endingColumn, creationContext );

		final var dialect = creationContext.getDialect();
		if ( creationContext.getSessionFactory()
				.getChangesetCoordinator()
				.useServerTimestamp( dialect ) ) {
			currentTimestampFunctionName = dialect.currentTimestamp();
			currentTimestampExpression =
					new SelfRenderingSqlFragmentExpression( currentTimestampFunctionName, jdbcMapping );
		}
		else {
			currentTimestampFunctionName = null;
			currentTimestampExpression = null;
		}
	}

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SelectableMapping endingColumnMapping(
			String tableName,
			Column endingColumn,
			RuntimeModelCreationContext creationContext) {
		return SelectableMappingImpl.from(
				tableName,
				endingColumn,
				null,
				null,
				jdbcMapping,
				true,
				true,
				false,
				false,
				creationContext
		);
	}

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SelectableMapping startingColumnMapping(
			String tableName,
			Column startingColumn,
			RuntimeModelCreationContext creationContext) {
		return SelectableMappingImpl.from(
				tableName,
				startingColumn,
				null,
				null,
				jdbcMapping,
				true,
				false,
				false,
				false,
				creationContext
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableName() {
		return tableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectableMapping getStartingColumnMapping() {
		return startingColumnMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectableMapping getEndingColumnMapping() {
		return endingColumnMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private Predicate createCurrentRestriction(TableReference tableReference) {
		return createCurrentRestriction( tableReference, null );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Predicate createCurrentRestriction(TableReference tableReference, SqlExpressionResolver expressionResolver) {
		final var endingColumn = resolveColumn( tableReference, expressionResolver, endingColumnMapping );
		return new NullnessPredicate( endingColumn, false, jdbcMapping );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private Predicate createRestriction(TableReference tableReference, Object temporalValue) {
		return createRestriction( tableReference, null, temporalValue );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Predicate createRestriction(
			TableReference tableReference,
			SqlExpressionResolver expressionResolver,
			Object temporalValue) {
		final var startingColumn = resolveColumn( tableReference, expressionResolver, startingColumnMapping );
		final var endingColumn = resolveColumn( tableReference, expressionResolver, endingColumnMapping );

		final Expression startingTemporalValue;
		final Expression endingTemporalValue;
		if ( currentTimestampExpression == null || temporalValue != null ) {
			startingTemporalValue = new TemporalJdbcParameter( startingColumnMapping );
			endingTemporalValue = new TemporalJdbcParameter( endingColumnMapping );
		}
		else {
			startingTemporalValue = currentTimestampExpression;
			endingTemporalValue = currentTimestampExpression;
		}

		final var startingPredicate = new ComparisonPredicate( startingColumn, LESS_THAN_OR_EQUAL, startingTemporalValue );
		final var endingNullPredicate = new NullnessPredicate( endingColumn, false, jdbcMapping );
		final var endingAfterPredicate = new ComparisonPredicate( endingColumn, GREATER_THAN, endingTemporalValue );

		final var endingPredicate = new Junction( Junction.Nature.DISJUNCTION );
		endingPredicate.add( endingNullPredicate );
		endingPredicate.add( endingAfterPredicate );

		final var predicate = new Junction( Junction.Nature.CONJUNCTION );
		predicate.add( startingPredicate );
		predicate.add( endingPredicate );

		return predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBinding createStartingValueBinding(ColumnReference startingColumnReference) {
		return createTemporalValueBinding( startingColumnReference, startingColumnMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBinding createEndingValueBinding(ColumnReference endingColumnReference) {
		return createTemporalValueBinding( endingColumnReference, endingColumnMapping );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private ColumnValueBinding createTemporalValueBinding(
			ColumnReference endingColumnReference, SelectableMapping columnMapping) {
		return new ColumnValueBinding( endingColumnReference,
				currentTimestampFunctionName != null
						? new ColumnWriteFragment( currentTimestampFunctionName, emptyList(), columnMapping )
						: new ColumnWriteFragment( "?",
								new ColumnValueParameter( endingColumnReference ),
								columnMapping ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBinding createNullEndingValueBinding(ColumnReference endingColumnReference) {
		return new ColumnValueBinding( endingColumnReference,
				new ColumnWriteFragment( null, emptyList(), endingColumnMapping ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private Expression resolveColumn(
			TableReference tableReference,
			SqlExpressionResolver expressionResolver,
			SelectableMapping selectableMapping) {
		return expressionResolver != null
				? expressionResolver.resolveSqlExpression( tableReference, selectableMapping )
				: new ColumnReference( tableReference, selectableMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addToInsertGroup(MutationGroupBuilder insertGroupBuilder, EntityPersister persister) {
		if ( temporalTableStrategy == TemporalTableStrategy.SINGLE_TABLE ) {
			final TableInsertBuilder insertBuilder =
					insertGroupBuilder.getTableDetailsBuilder( persister.getIdentifierTableName() );
			final var mutatingTable = insertBuilder.getMutatingTable();

			final var startingColumn = new ColumnReference( mutatingTable, getStartingColumnMapping() );
			insertBuilder.addValueColumn( createStartingValueBinding( startingColumn ) );

			final var endingColumn = new ColumnReference( mutatingTable, getEndingColumnMapping() );
			insertBuilder.addValueColumn( createNullEndingValueBinding( endingColumn ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(
			EntityMappingType associatedEntityMappingType,
			Consumer<Predicate> predicateConsumer,
			LazyTableGroup lazyTableGroup,
			NavigablePath navigablePath,
			SqlAstCreationState creationState) {
		if ( useTemporalRestriction( creationState ) ) {
			final var tableReference = lazyTableGroup.resolveTableReference( navigablePath, getTableName() );
			final var temporalInstant = creationState.getLoadQueryInfluencers().getTemporalIdentifier();
			final var resolver = creationState.getSqlExpressionResolver();
			final var temporalPredicate =
					temporalInstant == null
							? createCurrentRestriction( tableReference, resolver )
							: createRestriction( tableReference, resolver, temporalInstant );
			predicateConsumer.accept( temporalPredicate );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(
			EntityMappingType associatedEntityDescriptor,
			Consumer<Predicate> predicateConsumer,
			TableGroup tableGroup,
			SqlAliasBaseGenerator sqlAliasBaseGenerator,
			LoadQueryInfluencers influencers) {
		if ( useTemporalRestriction( influencers ) ) {
			final var instant = influencers.getTemporalIdentifier();
			final var primaryTableReference =
					tableGroup.resolveTableReference( getTableName() );
			predicateConsumer.accept( instant == null
					? createCurrentRestriction( primaryTableReference )
					: createRestriction( primaryTableReference, instant ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(
			PluralAttributeMapping associatedEntityDescriptor,
			Consumer<Predicate> predicateConsumer,
			TableGroup tableGroup,
			SqlAliasBaseGenerator sqlAliasBaseGenerator,
			LoadQueryInfluencers influencers) {
		if ( useTemporalRestriction( influencers ) ) {
			final var instant = influencers.getTemporalIdentifier();
			final var tableReference = tableGroup.resolveTableReference( getTableName() );
			predicateConsumer.accept( instant == null
					? createCurrentRestriction( tableReference )
					: createRestriction( tableReference, instant ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyPredicate(TableGroupJoin tableGroupJoin, LoadQueryInfluencers loadQueryInfluencers) {
		if ( useTemporalRestriction( loadQueryInfluencers ) ) {
			final var temporalInstant = loadQueryInfluencers.getTemporalIdentifier();
			final var tableReference = tableGroupJoin.getJoinedGroup().resolveTableReference( getTableName() );
			tableGroupJoin.applyPredicate( temporalInstant == null
					? createCurrentRestriction( tableReference )
					: createRestriction( tableReference, temporalInstant ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(
			Supplier<Consumer<Predicate>> predicateCollector,
			SqlAstCreationState creationState,
			TableGroup tableGroup,
			NamedTableReference rootTableReference,
			EntityMappingType entityMappingType) {
		if ( useTemporalRestriction( creationState ) ) {
			final var tableReference =
					tableGroup.resolveTableReference( getTableName() );
			final var temporalInstant =
					creationState.getLoadQueryInfluencers().getTemporalIdentifier();
			final var resolver = creationState.getSqlExpressionResolver();
			predicateCollector.get()
					.accept( temporalInstant == null
							? createCurrentRestriction( tableReference, resolver )
							: createRestriction( tableReference, resolver, temporalInstant ) );
			if ( tableReference != rootTableReference && creationState.supportsEntityNameUsage() ) {
				creationState.registerEntityNameUsage( tableGroup, EntityNameUse.EXPRESSION,
						entityMappingType.getRootEntityDescriptor().getEntityName() );
			}
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static boolean useTemporalRestriction(LoadQueryInfluencers influencers) {
		return !influencers.isAllRevisions()
			&& influencers.getSessionFactory().getJdbcServices().getDialect().getTemporalTableSupport()
						.useTemporalRestriction( influencers );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean useTemporalRestriction(SqlAstCreationState creationState) {
		final var influencers = creationState.getLoadQueryInfluencers();
		return !influencers.isAllRevisions()
			&& creationState.getCreationContext().getDialect().getTemporalTableSupport()
						.useTemporalRestriction( influencers );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useAuxiliaryTable(LoadQueryInfluencers influencers) {
		return temporalTableStrategy == TemporalTableStrategy.HISTORY_TABLE
			&& influencers.getTemporalIdentifier() != null
			&& !influencers.isAllRevisions();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAffectedByInfluencers(LoadQueryInfluencers influencers) {
		return influencers.getTemporalIdentifier() != null
			&& !influencers.isAllRevisions();
	}
}
