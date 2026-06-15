/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import jakarta.persistence.AccessType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common interface for JAXB bindings that represent persistent attributes.
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JaxbPersistentAttribute {
	/**
	 * The attribute's name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setName(String name);

	/**
	 * JPA's way to specify an access-strategy
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessType getAccess();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAccess(AccessType accessType);

	/**
	 * Hibernate's pluggable access-strategy support
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAttributeAccessor();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAttributeAccessor(String value);
}
