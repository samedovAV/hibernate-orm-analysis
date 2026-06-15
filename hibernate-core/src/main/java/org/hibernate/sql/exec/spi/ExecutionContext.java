/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.resource.jdbc.spi.LogicalConnectionImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A context for execution of SQL statements expressed via
 * SQL AST and JdbcOperation
 */
public interface ExecutionContext {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isScrollResult(){
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isTransactionActive() {
		return getSession().isTransactionInProgress();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryOptions getQueryOptions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadQueryInfluencers getLoadQueryInfluencers();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings getQueryParameterBindings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Callback getCallback();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasCallbackActions() {
		final Callback callback = getCallback();
		return callback != null && callback.hasAfterLoadActions();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQueryIdentifier(String sql);

	/**
	 * Get the collection key for the collection which is to be loaded immediately.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CollectionKey getCollectionKey() {
		return null;
	}

	/**
	 * Should only be used when initializing a bytecode-proxy
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object getEntityInstance() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object getEntityId() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getEntityUniqueKeyAttributePath() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object getEntityUniqueKey() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityMappingType getRootEntityDescriptor() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void registerLoadingEntityHolder(EntityHolder holder) {
		// by default do nothing
	}

	/**
	 * Hook to allow delaying calls to {@link LogicalConnectionImplementor#afterStatement()}.
	 * Mainly used in the case of batching and multi-table mutations
	 *
	 */
	// todo (6.0) : come back and make sure we are calling this at appropriate times.
	// Despite the name, it should be called after a logical group of statements - e.g.,
	// after all of the delete statements against all of the tables for a particular entity
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void afterStatement(LogicalConnectionImplementor logicalConnection) {
		logicalConnection.afterStatement();
	}

	/**
	 * Determine if the query execution has to be considered by the {@link org.hibernate.stat.Statistics}.
	 *
	 * @return true if the query execution has to be added to the {@link org.hibernate.stat.Statistics}, false otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean hasQueryExecutionToBeAddedToStatistics() {
		return false;
	}

	/**
	 * Does this query return objects that might be already cached
	 * by the session, whose lock mode may need upgrading
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean upgradeLocks(){
		return false;
	}

}
