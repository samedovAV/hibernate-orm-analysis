/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.spi;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.persister.collection.CollectionPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension of {@link CollectionSemantics} for Maps
 *
 * @author Steve Ebersole
 */
public interface MapSemantics<MKV extends Map<K,V>,K,V> extends CollectionSemantics<MKV,V> {
	/**
	 * Create a raw (unwrapped) version of the map and populate it
	 * with the given entries.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<KK, VV> Map<KK, VV> instantiateWithElements(
			int anticipatedSize,
			CollectionPersister collectionDescriptor,
			Map<? extends KK, ? extends VV> entries);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Iterator<K> getKeyIterator(MKV rawMap);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitKeys(MKV rawMap, Consumer<? super K> action);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitEntries(MKV rawMap, BiConsumer<? super K,? super V> action);
}
