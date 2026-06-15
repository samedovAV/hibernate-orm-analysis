/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.collection.spi.CollectionSemantics;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * MappingType descriptor for the collection Java type (List, Set, etc)
 *
 * @author Steve Ebersole
 */
public interface CollectionMappingType<C> extends MappingType {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionSemantics<C,?> getCollectionSemantics();
}
