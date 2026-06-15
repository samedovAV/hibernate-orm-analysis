/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.results.graph.collection.CollectionInitializer;
import org.hibernate.sql.results.graph.collection.LoadingCollectionEntry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a collection currently being loaded.
 *
 * @author Steve Ebersole
 */
public class LoadingCollectionEntryImpl implements LoadingCollectionEntry {
	private final CollectionPersister collectionDescriptor;
	private final CollectionInitializer<?> initializer;
	private final Object key;
	private final PersistentCollection<?> collectionInstance;
	private final List<Object> loadingState = new ArrayList<>();

	public LoadingCollectionEntryImpl(
			CollectionPersister collectionDescriptor,
			CollectionInitializer<?> initializer,
			Object key,
			PersistentCollection<?> collectionInstance) {
		this.collectionDescriptor = collectionDescriptor;
		this.initializer = initializer;
		this.key = key;
		this.collectionInstance = collectionInstance;

		collectionInstance.beforeInitialize( collectionDescriptor, -1 );
		collectionInstance.beginRead();
	}

	@Override @Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionPersister getCollectionDescriptor() {
		return collectionDescriptor;
	}

	/**
	 * Access to the initializer that is responsible for initializing this collection
	 */
	@Override @Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionInitializer<?> getInitializer() {
		return initializer;
	}

	@Override @Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getKey() {
		return key;
	}

	@Override @Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentCollection<?> getCollectionInstance() {
		return collectionInstance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void load(Consumer<List<Object>> loadingEntryConsumer) {
		loadingEntryConsumer.accept( loadingState );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> void load(T arg1, BiConsumer<T, List<Object>> loadingEntryConsumer) {
		loadingEntryConsumer.accept( arg1, loadingState );
	}

	@Override @Prove(complexity = Complexity.O_1, n = "", count = {})
	public void finishLoading(ExecutionContext executionContext) {
		collectionInstance.injectLoadedState(
				getCollectionDescriptor().getAttributeMapping(),
				loadingState
		);

		final boolean hasNoQueuedAdds = collectionInstance.endRead();
		final SharedSessionContractImplementor session = executionContext.getSession();
		final PersistenceContext persistenceContext = session.getPersistenceContextInternal();
		final CollectionPersister collectionDescriptor = getCollectionDescriptor();

		ResultsHelper.finalizeCollectionLoading(
				persistenceContext,
				collectionDescriptor,
				collectionInstance,
				getKey(),
				hasNoQueuedAdds,
				initializer.getFetchOptions().cacheStoreMode()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return getClass().getSimpleName() + "(" + getCollectionDescriptor().getNavigableRole().getFullPath() + "#" + getKey() + ")";
	}
}
