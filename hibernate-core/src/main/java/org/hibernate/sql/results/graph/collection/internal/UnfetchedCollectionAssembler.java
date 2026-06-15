/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.collection.internal;

import org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.sql.results.graph.DomainResultAssembler;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class UnfetchedCollectionAssembler implements DomainResultAssembler {

	private final PluralAttributeMapping fetchedMapping;

	public UnfetchedCollectionAssembler(PluralAttributeMapping fetchedMapping) {
		this.fetchedMapping = fetchedMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object assemble(RowProcessingState rowProcessingState) {
		return LazyPropertyInitializer.UNFETCHED_PROPERTY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType getAssembledJavaType() {
		return fetchedMapping.getJavaType();
	}

}
