/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.internal;

import org.hibernate.mapping.Property;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Bundle's a Hibernate property mapping together with the JPA metamodel information
 * of the attribute owner.
 *
 * @param <X> The owner type.
 */
public interface AttributeContext<X> {
	/**
	 * Retrieve the attribute owner.
	 *
	 * @return The owner.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<X> getOwnerType();

	/**
	 * Retrieve the Hibernate property mapping.
	 *
	 * @return The Hibernate property mapping.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Property getPropertyMapping();
}
