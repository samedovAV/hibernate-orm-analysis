/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.DiscriminatorValueDetails;
import org.hibernate.metamodel.mapping.EmbeddableDiscriminatorConverter;
import org.hibernate.metamodel.mapping.EmbeddableDiscriminatorMapping;
import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of {@link DiscriminatorValueDetails} used for embeddable inheritance.
 *
 * @author Marco Belladelli
 * @see EmbeddableDiscriminatorConverter
 * @see EmbeddableDiscriminatorMapping
 */
public class EmbeddableDiscriminatorValueDetailsImpl implements DiscriminatorValueDetails {
	final Object value;
	final Class<?> embeddableClass;

	public EmbeddableDiscriminatorValueDetailsImpl(Object value, Class<?> embeddableClass) {
		this.value = value;
		this.embeddableClass = embeddableClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> getEmbeddableClass() {
		return embeddableClass;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getValue() {
		return value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIndicatedEntityName() {
		return embeddableClass.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getIndicatedEntity() {
		throw new UnsupportedOperationException();
	}
}
