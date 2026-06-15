/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.select;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.query.common.FetchClauseType;
import org.hibernate.query.sqm.tree.expression.SqmAliasedNodeRef;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public abstract class QueryPart implements SqlAstNode {
	private final boolean isRoot;

	private boolean hasPositionalSortItem;
	private List<SortSpecification> sortSpecifications;

	private Expression offsetClauseExpression;
	private Expression fetchClauseExpression;
	private FetchClauseType fetchClauseType = FetchClauseType.ROWS_ONLY;

	public QueryPart(boolean isRoot) {
		this.isRoot = isRoot;
	}

	protected QueryPart(boolean isRoot, QueryPart original) {
		this.isRoot = isRoot;
		this.hasPositionalSortItem = original.hasPositionalSortItem;
		this.sortSpecifications = original.sortSpecifications;
		this.offsetClauseExpression = original.offsetClauseExpression;
		this.fetchClauseExpression = original.fetchClauseExpression;
		this.fetchClauseType = original.fetchClauseType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract QuerySpec getFirstQuerySpec();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract QuerySpec getLastQuerySpec();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract void visitQuerySpecs(Consumer<QuerySpec> querySpecConsumer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public abstract <T> T queryQuerySpecs(Function<QuerySpec, T> querySpecConsumer);

	/**
	 * Does this QueryPart map to the statement's root query (as
	 * opposed to one of its sub-queries)?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isRoot() {
		return isRoot;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasSortSpecifications() {
		return sortSpecifications != null && !sortSpecifications.isEmpty();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasPositionalSortItem() {
		return hasPositionalSortItem;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SortSpecification> getSortSpecifications() {
		return sortSpecifications;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitSortSpecifications(Consumer<SortSpecification> consumer) {
		if ( sortSpecifications != null ) {
			sortSpecifications.forEach( consumer );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addSortSpecification(SortSpecification specification) {
		if ( sortSpecifications == null ) {
			sortSpecifications = new ArrayList<>();
		}
		sortSpecifications.add( specification );

		if ( isRoot ) {
			if ( specification.getSortExpression() instanceof SqmAliasedNodeRef ) {
				hasPositionalSortItem = true;
			}
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasOffsetOrFetchClause() {
		return offsetClauseExpression != null || fetchClauseExpression != null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getOffsetClauseExpression() {
		return offsetClauseExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOffsetClauseExpression(Expression offsetClauseExpression) {
		this.offsetClauseExpression = offsetClauseExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression getFetchClauseExpression() {
		return fetchClauseExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFetchClauseExpression(Expression fetchClauseExpression, FetchClauseType fetchClauseType) {
		if ( fetchClauseExpression == null ) {
			this.fetchClauseExpression = null;
			this.fetchClauseType = FetchClauseType.ROWS_ONLY;
		}
		else {
			if ( fetchClauseType == null ) {
				throw new IllegalArgumentException( "Fetch clause may not be null" );
			}
			this.fetchClauseExpression = fetchClauseExpression;
			this.fetchClauseType = fetchClauseType;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchClauseType getFetchClauseType() {
		return fetchClauseType;
	}

}
