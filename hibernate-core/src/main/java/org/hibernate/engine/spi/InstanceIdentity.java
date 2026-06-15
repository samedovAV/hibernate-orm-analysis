/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for classes whose instances are uniquely identifiable through a simple {@code int} value,
 * and can be mapped via {@link org.hibernate.internal.util.collections.InstanceIdentityMap}.
 */
public interface InstanceIdentity {
	/**
	 * Retrieve the unique identifier of this instance
	 *
	 * @return the unique instance identifier
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int $$_hibernate_getInstanceId();

	/**
	 * Set the value of the unique identifier for this instance
	 *
	 * @param instanceId the unique identifier value to set
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void $$_hibernate_setInstanceId(int instanceId);
}
