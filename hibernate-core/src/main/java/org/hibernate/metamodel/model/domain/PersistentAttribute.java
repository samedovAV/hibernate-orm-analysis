/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.metamodel.Attribute;

import org.hibernate.metamodel.AttributeClassification;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Hibernate extension to the JPA {@link Attribute} contract
 *
 * @author Steve Ebersole
 */
public interface PersistentAttribute<D,J> extends Attribute<D,J> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<D> getDeclaringType();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<J> getAttributeJavaType();

	/**
	 * The classification of the attribute (is it a basic type, entity, etc)
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeClassification getAttributeClassification();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainType<?> getValueGraphType();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<?> getKeyGraphType();
}
