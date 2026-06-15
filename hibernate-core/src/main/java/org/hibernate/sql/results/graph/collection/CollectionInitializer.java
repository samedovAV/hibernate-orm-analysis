/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.FetchOptions;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Initializer implementation for initializing collections (plural attributes)
 *
 * @author Steve Ebersole
 */
public interface CollectionInitializer<Data extends InitializerData> extends InitializerParent<Data> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeMapping getInitializedPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CollectionPersister getInitializingCollectionDescriptor() {
		return getInitializedPart().getCollectionDescriptor();
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentCollection<?> getCollectionInstance(Data data);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default @Nullable PersistentCollection<?> getCollectionInstance(RowProcessingState rowProcessingState) {
		return getCollectionInstance( getData( rowProcessingState ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default FetchOptions getFetchOptions() {
		return FetchOptions.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isCollectionInitializer() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CollectionInitializer<?> asCollectionInitializer() {
		return this;
	}
}
