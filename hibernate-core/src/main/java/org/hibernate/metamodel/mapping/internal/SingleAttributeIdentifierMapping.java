/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.generator.Generator;
import org.hibernate.metamodel.mapping.AttributeMetadata;
import org.hibernate.metamodel.mapping.EntityIdentifierMapping;
import org.hibernate.metamodel.mapping.ManagedMappingType;
import org.hibernate.metamodel.mapping.PropertyBasedMapping;
import org.hibernate.metamodel.mapping.SingularAttributeMapping;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.hibernate.type.descriptor.java.MutabilityPlan;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SingleAttributeIdentifierMapping extends EntityIdentifierMapping, PropertyBasedMapping,
		SingularAttributeMapping, AttributeMetadata {
	/**
	 * Access to the identifier attribute's PropertyAccess
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PropertyAccess getPropertyAccess();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAttributeName();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getPartName() {
		return ID_ROLE_NAME;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Generator getGenerator() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getStateArrayPosition() {
		return -1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default AttributeMetadata getAttributeMetadata() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ManagedMappingType getDeclaringType() {
		return findContainingEntityMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSelectable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isNullable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isInsertable() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isUpdatable() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isIncludedInDirtyChecking() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isIncludedInOptimisticLocking() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MutabilityPlan getMutabilityPlan() {
		return ImmutableMutabilityPlan.instance();
	}
}
