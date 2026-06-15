/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.sql.exec.spi.ExecutionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a collection currently being loaded.
 *
 * @author Steve Ebersole
 */
public interface LoadingCollectionEntry {
	/**
	 * The descriptor for the collection being loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionPersister getCollectionDescriptor();

	/**
	 * The initializer responsible for the loading
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionInitializer<?> getInitializer();

	/**
	 * The collection key.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getKey();

	/**
	 * The collection instance being loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentCollection<?> getCollectionInstance();

	/**
	 * Callback for row loading.  Allows delayed List creation
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void load(Consumer<List<Object>> loadingEntryConsumer);

	/**
	 * Callback for row loading.  Allows delayed List creation
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <T> void load(T arg1, BiConsumer<T, List<Object>> loadingEntryConsumer) {
		load( list -> loadingEntryConsumer.accept( arg1, list ) );
	}

	/**
	 * Complete the load
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishLoading(ExecutionContext executionContext);
}
