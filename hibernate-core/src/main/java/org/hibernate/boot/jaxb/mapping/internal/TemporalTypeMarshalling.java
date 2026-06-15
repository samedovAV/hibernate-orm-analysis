/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.TemporalType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link TemporalType}
 *
 * @author Steve Ebersole
 */
public class TemporalTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static TemporalType fromXml(String name) {
		return name == null ? null : TemporalType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(TemporalType temporalType) {
		return temporalType == null ? null : temporalType.name();
	}
}
