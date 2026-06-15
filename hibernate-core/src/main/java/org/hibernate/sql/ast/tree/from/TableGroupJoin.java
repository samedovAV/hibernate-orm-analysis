/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import org.hibernate.spi.NavigablePath;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlAstTreeHelper;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.predicate.PredicateContainer;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class TableGroupJoin implements TableJoin, PredicateContainer, DomainResultProducer {
	private final NavigablePath navigablePath;
	private final TableGroup joinedGroup;

	private SqlAstJoinType joinType;
	private Predicate predicate;

	public TableGroupJoin(
			NavigablePath navigablePath,
			SqlAstJoinType joinType,
			TableGroup joinedGroup) {
		this( navigablePath, joinType, joinedGroup, null );
	}

	public TableGroupJoin(
			NavigablePath navigablePath,
			SqlAstJoinType joinType,
			TableGroup joinedGroup,
			Predicate predicate) {
		assert !joinedGroup.isLateral() || ( joinType == SqlAstJoinType.INNER
				|| joinType == SqlAstJoinType.LEFT
				|| joinType == SqlAstJoinType.CROSS )
				: "Lateral is only allowed with inner, left or cross joins";
		this.navigablePath = navigablePath;
		this.joinType = joinType;
		this.joinedGroup = joinedGroup;
		this.predicate = predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstJoinType getJoinType() {
		return joinType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setJoinType(SqlAstJoinType joinType) {
//		SqlTreeCreationLogger.LOGGER.tracef(
//				"Adjusting join-type for TableGroupJoin(%s) : %s -> %s",
//				navigablePath,
//				this.joinType,
//				joinType
//		);
		this.joinType = joinType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup getJoinedGroup() {
		return joinedGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqlAstNode getJoinedNode() {
		return joinedGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyPredicate(Predicate predicate) {
		this.predicate = SqlAstTreeHelper.combinePredicates( this.predicate, predicate );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitTableGroupJoin( this );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInitialized() {
		return joinedGroup.isInitialized();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isImplicit() {
		return !navigablePath.isAliased();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResult<?> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState) {
		return getJoinedGroup().createDomainResult( resultVariable, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		getJoinedGroup().applySqlSelections( creationState );
	}
}
