/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.engine.spi.VersionValue;
import org.hibernate.metamodel.mapping.internal.BasicAttributeMapping;
import org.hibernate.type.descriptor.java.VersionJavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the mapping of an entity's version
 *
 * @see jakarta.persistence.Version
 */
public interface EntityVersionMapping extends BasicValuedModelPart {

	String VERSION_ROLE_NAME = "{version}";

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static boolean matchesRoleName(String name) {
		return VERSION_ROLE_NAME.equals( name );
	}

	/**
	 * The attribute marked as the version
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicAttributeMapping getVersionAttribute();

	/**
	 * The strategy for distinguishing between detached and transient
	 * state based on the version mapping.
	 *
	 * @see EntityIdentifierMapping#getUnsavedStrategy()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	VersionValue getUnsavedStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	VersionJavaType<?> getJavaType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default VersionJavaType<?> getExpressibleJavaType() {
		return (VersionJavaType<?>) getMappedType().getMappedJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default AttributeMapping asAttributeMapping() {
		return getVersionAttribute();
	}
}
