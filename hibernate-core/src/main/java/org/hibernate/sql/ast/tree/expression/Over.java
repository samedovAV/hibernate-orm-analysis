/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import java.util.List;

import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.query.common.FrameExclusion;
import org.hibernate.query.common.FrameKind;
import org.hibernate.query.common.FrameMode;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.select.SortSpecification;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class Over<T> implements Expression, DomainResultProducer<T> {

	private final Expression expression;
	private final List<Expression> partitions;
	private final List<SortSpecification> orderList;
	private final FrameMode mode;
	private final FrameKind startKind;
	private final Expression startExpression;
	private final FrameKind endKind;
	private final Expression endExpression;
	private final FrameExclusion exclusion;

	public Over(
			Expression expression,
			List<Expression> partitions,
			List<SortSpecification> orderList) {
		this.expression = expression;
		this.partitions = partitions;
		this.orderList = orderList;
		this.mode = FrameMode.RANGE;
		this.startKind = FrameKind.UNBOUNDED_PRECEDING;
		this.startExpression = null;
		this.endKind = FrameKind.CURRENT_ROW;
		this.endExpression = null;
		this.exclusion = FrameExclusion.NO_OTHERS;
	}

	public Over(
			Expression expression,
			List<Expression> partitions,
			List<SortSpecification> orderList,
			FrameMode mode,
			FrameKind startKind,
			Expression startExpression,
			FrameKind endKind,
			Expression endExpression,
			FrameExclusion exclusion) {
		this.expression = expression;
		this.partitions = partitions;
		this.orderList = orderList;
		this.mode = mode;
		this.startKind = startKind;
		this.startExpression = startExpression;
		this.endKind = endKind;
		this.endExpression = endExpression;
		this.exclusion = exclusion;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getExpression() {
		return expression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Expression> getPartitions() {
		return partitions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SortSpecification> getOrderList() {
		return orderList;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FrameMode getMode() {
		return mode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FrameKind getStartKind() {
		return startKind;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getStartExpression() {
		return startExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FrameKind getEndKind() {
		return endKind;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getEndExpression() {
		return endExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FrameExclusion getExclusion() {
		return exclusion;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JdbcMappingContainer getExpressionType() {
		return expression.getExpressionType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker walker) {
		walker.visitOver( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResult<T> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		final SqlSelection sqlSelection = createSelection( creationState.getSqlAstCreationState() );
		return new BasicResult<>(
				sqlSelection.getValuesArrayPosition(),
				resultVariable,
				expression.getExpressionType().getSingleJdbcMapping()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		createSelection( creationState.getSqlAstCreationState() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SqlSelection createSelection(SqlAstCreationState creationState) {
		return creationState.getSqlExpressionResolver().resolveSqlSelection(
				this,
				expression.getExpressionType().getSingleJdbcMapping().getJdbcJavaType(),
				null,
				creationState.getCreationContext().getTypeConfiguration()
		);
	}

}
