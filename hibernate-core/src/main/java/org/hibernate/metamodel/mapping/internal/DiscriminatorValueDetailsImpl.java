/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.metamodel.mapping.DiscriminatorValue;
import org.hibernate.metamodel.mapping.DiscriminatorValueDetails;
import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DiscriminatorValueDetailsImpl implements DiscriminatorValueDetails {
	private final DiscriminatorValue value;
	private final EntityMappingType matchedEntityDescriptor;

	public DiscriminatorValueDetailsImpl(DiscriminatorValue value, EntityMappingType matchedEntityDescriptor) {
		this.value = value;
		this.matchedEntityDescriptor = matchedEntityDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getValue() {
		return value.value();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getIndicatedEntity() {
		return matchedEntityDescriptor;
	}
}
