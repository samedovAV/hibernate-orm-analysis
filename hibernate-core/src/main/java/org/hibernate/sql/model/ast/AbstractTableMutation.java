/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.engine.jdbc.mutation.internal.MutationQueryOptions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ValuesAnalysis;
import org.hibernate.sql.model.jdbc.JdbcMutationOperation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base {@link TableMutation} support
 *
 * @author Steve Ebersole
 */
public abstract class AbstractTableMutation<O extends MutationOperation>
		implements TableMutation<O> {
	private final MutatingTableReference mutatingTable;
	private final MutationTarget<?,?> mutationTarget;
	private final String sqlComment;

	private final List<ColumnValueParameter> parameters;

	public AbstractTableMutation(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String sqlComment,
			List<ColumnValueParameter> parameters) {
		this.mutatingTable = mutatingTable;
		this.mutationTarget = mutationTarget;
		this.sqlComment = sqlComment;
		this.parameters = parameters;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		final String type = isCustomSql() ? "custom-sql" : "generated";
		return getLoggableName() + "(" + getMutationTarget().getRolePath() + " : " + type + ")";
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCustomSql() {
		return this instanceof CustomSqlMutation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract String getLoggableName();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutatingTableReference getMutatingTable() {
		return mutatingTable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutationTarget<?,?> getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getMutationComment() {
		return sqlComment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnValueParameter> getParameters() {
		return parameters;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void forEachParameter(Consumer<ColumnValueParameter> consumer) {
		if ( parameters == null ) {
			return;
		}

		for ( int i = 0; i < parameters.size(); i++ ) {
			consumer.accept( parameters.get( i ) );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected static  <T> void forEachThing(List<T> list, BiConsumer<Integer,T> action) {
		if ( list == null ) {
			return;
		}

		for ( int i = 0; i < list.size(); i++ ) {
			action.accept( i, list.get( i ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public O createMutationOperation(ValuesAnalysis valuesAnalysis, SessionFactoryImplementor factory) {
		final SqlAstTranslatorFactory sqlAstTranslatorFactory = factory
				.getJdbcServices()
				.getJdbcEnvironment()
				.getSqlAstTranslatorFactory();
		//noinspection unchecked
		final SqlAstTranslator<JdbcMutationOperation> translator = sqlAstTranslatorFactory.buildModelMutationTranslator(
				(TableMutation<JdbcMutationOperation>) this,
				factory
		);

		//noinspection unchecked
		return (O) translator.translate( null, MutationQueryOptions.INSTANCE );
	}

	/**
	 * Intended for use from {@link SqlAstTranslator}
	 */
	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public final O createMutationOperation(String sql, List<JdbcParameterBinder> parameterBinders) {
		return createMutationOperation( getMutatingTable().getTableMapping(), sql, parameterBinders );
	}

	/**
	 * Intended for use from {@link SqlAstTranslator}
	 *
	 * @param effectiveBinders The parameter binders effective for this table mutation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract O createMutationOperation(
			TableMapping tableDetails,
			String sql,
			List<JdbcParameterBinder> effectiveBinders);
}
