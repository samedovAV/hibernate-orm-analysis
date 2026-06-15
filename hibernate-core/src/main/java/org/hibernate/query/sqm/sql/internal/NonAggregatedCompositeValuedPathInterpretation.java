/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql.internal;

import org.hibernate.metamodel.mapping.NonAggregatedIdentifierMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.query.sqm.sql.SqmToSqlAstConverter;
import org.hibernate.query.sqm.tree.domain.NonAggregatedCompositeSimplePath;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.hibernate.sql.ast.tree.expression.SqlTupleContainer;
import org.hibernate.sql.ast.tree.from.TableGroup;

import jakarta.annotation.Nullable;

import static org.hibernate.query.sqm.internal.SqmUtil.determineAffectedTableName;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class NonAggregatedCompositeValuedPathInterpretation<T>
		extends AbstractSqmPathInterpretation<T>
		implements SqlTupleContainer {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <T> NonAggregatedCompositeValuedPathInterpretation<T> from(
			NonAggregatedCompositeSimplePath<T> sqmPath,
			SqmToSqlAstConverter converter,
			SqmToSqlAstConverter sqlAstCreationState) {
		final var tableGroup =
				sqlAstCreationState.getFromClauseAccess()
						.findTableGroup( sqmPath.getLhs().getNavigablePath() );
		final var mapping =
				(NonAggregatedIdentifierMapping)
						tableGroup.getModelPart()
								.findSubPart( sqmPath.getReferencedPathSource().getPathName(), null );

		return new NonAggregatedCompositeValuedPathInterpretation<>(
				mapping.toSqlExpression(
						tableGroup,
						converter.getCurrentClauseStack().getCurrent(),
						converter,
						converter
				),
				sqmPath.getNavigablePath(),
				mapping,
				tableGroup,
				determineAffectedTableName( tableGroup, mapping )
		);
	}

	private final SqlTuple sqlExpression;
	private final @Nullable String affectedTableName;

	private NonAggregatedCompositeValuedPathInterpretation(
			SqlTuple sqlExpression,
			NavigablePath navigablePath,
			NonAggregatedIdentifierMapping mapping,
			TableGroup tableGroup,
			@Nullable String affectedTableName) {
		super( navigablePath, mapping, tableGroup );
		this.sqlExpression = sqlExpression;
		this.affectedTableName = affectedTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlTuple getSqlExpression() {
		return sqlExpression;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable String getAffectedTableName() {
		return affectedTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlExpression.accept( sqlTreeWalker );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlTuple getSqlTuple() {
		return sqlExpression;
	}
}
