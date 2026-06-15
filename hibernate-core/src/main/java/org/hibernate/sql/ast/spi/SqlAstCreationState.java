/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import org.hibernate.Internal;
import org.hibernate.LockMode;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.metamodel.mapping.ordering.OrderByFragment;
import org.hibernate.persister.entity.EntityNameUse;
import org.hibernate.query.sqm.spi.SqmCreationContext;
import org.hibernate.sql.ast.tree.from.TableGroup;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to stuff used while creating a SQL AST
 *
 * @author Steve Ebersole
 */
public interface SqlAstCreationState {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstCreationContext getCreationContext();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAstProcessingState getCurrentProcessingState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlExpressionResolver getSqlExpressionResolver();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FromClauseAccess getFromClauseAccess();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlAliasBaseGenerator getSqlAliasBaseGenerator();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadQueryInfluencers getLoadQueryInfluencers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmCreationContext getSqmCreationContext() {
		return getCreationContext().getSessionFactory().getQueryEngine().getCriteriaBuilder();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean applyOnlyLoadByKeyFilters();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerLockMode(String identificationVariable, LockMode explicitLockMode);

	/**
	 * This callback is for handling of filters and is necessary to allow correct treat optimizations.
	 */
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void registerEntityNameUsage(
			TableGroup tableGroup,
			EntityNameUse entityNameUse,
			String hibernateEntityName) {
		// No-op
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean supportsEntityNameUsage() {
		return false;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void applyOrdering(TableGroup tableGroup, OrderByFragment orderByFragment) {
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isProcedureOrNativeQuery(){
		return false;
	}
}
