/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.AccessType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for JPA's {@link AccessType}
 *
 * @author Steve Ebersole
 */
public class AccessTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AccessType fromXml(String name) {
		return name == null ? null : AccessType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(AccessType accessType) {
		return accessType == null ? null : accessType.name();
	}
}
