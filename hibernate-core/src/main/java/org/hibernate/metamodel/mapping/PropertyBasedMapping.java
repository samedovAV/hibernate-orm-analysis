/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.property.access.spi.PropertyAccess;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes an attribute with a property access.
 *
 * @author Christian Beikov
 */
public interface PropertyBasedMapping {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PropertyAccess getPropertyAccess();
}
