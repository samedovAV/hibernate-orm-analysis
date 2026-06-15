/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.collection.internal;

import org.hibernate.collection.spi.CollectionSemantics;
import org.hibernate.collection.spi.CollectionSemanticsResolver;
import org.hibernate.mapping.Collection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard implementation of CollectionSemanticsResolver
 *
 * @author Steve Ebersole
 */
public class StandardCollectionSemanticsResolver implements CollectionSemanticsResolver {
	/**
	 * Singleton access
	 */
	public static final StandardCollectionSemanticsResolver INSTANCE = new StandardCollectionSemanticsResolver();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionSemantics resolveRepresentation(Collection bootDescriptor) {
		return bootDescriptor.getCollectionSemantics();
	}
}
