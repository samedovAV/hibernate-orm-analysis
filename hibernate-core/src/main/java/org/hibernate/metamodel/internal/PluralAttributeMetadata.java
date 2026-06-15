/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import org.hibernate.metamodel.CollectionClassification;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Attribute metadata contract for a plural attribute.
 *
 * @param <O> The owner type
 * @param <C> The attribute type (the collection type)
 * @param <E> The collection element type
 */
@SuppressWarnings("unused")
public interface PluralAttributeMetadata<O, C, E> extends AttributeMetadata<O, C> {
	/**
	 * The classification of the collection, indicating the collection semantics
	 * to be used.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionClassification getCollectionClassification();

	/**
	 * Retrieve the value context for the collection's elements.
	 *
	 * @return The value context for the collection's elements.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValueContext getElementValueContext();

	/**
	 * Retrieve the value context for the collection's keys (if a map, null otherwise).
	 *
	 * @return The value context for the collection's keys (if a map, null otherwise).
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValueContext getMapKeyValueContext();
}
