/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import java.util.List;

import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PreLoadEvent;
import org.hibernate.sql.results.spi.LoadContexts;
import org.hibernate.sql.results.graph.collection.LoadingCollectionEntry;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.sql.exec.spi.ExecutionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides a context for processing the processing of the complete
 * set of rows from a JdbcValuesSource.  Holds in-flight state
 * and provides access to environmental information needed to perform the
 * processing.
 *
 * @author Steve Ebersole
 */
public interface JdbcValuesSourceProcessingState {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ExecutionContext getExecutionContext();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getSession();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default QueryOptions getQueryOptions() {
		return getExecutionContext().getQueryOptions();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesSourceProcessingOptions getProcessingOptions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PreLoadEvent getPreLoadEvent();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PostLoadEvent getPostLoadEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerLoadingEntityHolder(EntityHolder holder);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<EntityHolder> getLoadingEntityHolders();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerReloadedEntityHolder(EntityHolder holder);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<EntityHolder> getReloadedEntityHolders();

	/**
	 * Find a LoadingCollectionEntry locally to this context.
	 *
	 * @see LoadContexts#findLoadingCollectionEntry(CollectionKey)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LoadingCollectionEntry findLoadingCollectionLocally(CollectionKey key);

	/**
	 * Registers a LoadingCollectionEntry locally to this context
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerLoadingCollection(
			CollectionKey collectionKey,
			LoadingCollectionEntry loadingCollectionEntry);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerSubselects();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp();
}
