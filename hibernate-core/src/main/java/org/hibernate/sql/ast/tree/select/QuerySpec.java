/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.select;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstTreeHelper;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.from.FromClause;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.predicate.PredicateContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class QuerySpec extends QueryPart implements SqlAstNode, PredicateContainer {

	private final FromClause fromClause;
	private final SelectClause selectClause;

	private Predicate whereClauseRestrictions;

	private List<Expression> groupByClauseExpressions = Collections.emptyList();
	private Predicate havingClauseRestrictions;

	private Set<NavigablePath> rootPathsForLocking;

	public QuerySpec(boolean isRoot) {
		super( isRoot );
		this.fromClause = new FromClause();
		this.selectClause = new SelectClause();
	}

	public QuerySpec(boolean isRoot, int expectedNumberOfRoots) {
		super( isRoot );
		this.fromClause = new FromClause( expectedNumberOfRoots );
		this.selectClause = new SelectClause();
	}

	private QuerySpec(QuerySpec original, boolean root) {
		super( root, original );
		this.fromClause = original.fromClause;
		this.selectClause = original.selectClause;
		this.whereClauseRestrictions = original.whereClauseRestrictions;
		this.groupByClauseExpressions = original.groupByClauseExpressions;
		this.havingClauseRestrictions = original.havingClauseRestrictions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpec asSubQuery() {
		return isRoot() ? new QuerySpec( this, false ) : this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpec asRootQuery() {
		return isRoot() ? this : new QuerySpec( this, true );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpec getFirstQuerySpec() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QuerySpec getLastQuerySpec() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitQuerySpecs(Consumer<QuerySpec> querySpecConsumer) {
		querySpecConsumer.accept( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> T queryQuerySpecs(Function<QuerySpec, T> querySpecConsumer) {
		return querySpecConsumer.apply( this );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FromClause getFromClause() {
		return fromClause;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectClause getSelectClause() {
		return selectClause;
	}

	/// Set of [NavigablePath] references to be considered roots
	/// for locking purposes.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<NavigablePath> getRootPathsForLocking() {
		return rootPathsForLocking;
	}

	/// Applies a [NavigablePath] to be considered a root for the
	/// purpose of potential locking.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyRootPathForLocking(NavigablePath path) {
		if ( rootPathsForLocking == null ) {
			rootPathsForLocking = new HashSet<>();
		}
		rootPathsForLocking.add( path );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getWhereClauseRestrictions() {
		return whereClauseRestrictions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(Predicate predicate) {
		this.whereClauseRestrictions = SqlAstTreeHelper.combinePredicates( this.whereClauseRestrictions, predicate );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Expression> getGroupByClauseExpressions() {
		return groupByClauseExpressions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setGroupByClauseExpressions(List<Expression> groupByClauseExpressions) {
		this.groupByClauseExpressions = groupByClauseExpressions == null ? Collections.emptyList() : groupByClauseExpressions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getHavingClauseRestrictions() {
		return havingClauseRestrictions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setHavingClauseRestrictions(Predicate havingClauseRestrictions) {
		this.havingClauseRestrictions = havingClauseRestrictions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitQuerySpec( this );
	}

}
