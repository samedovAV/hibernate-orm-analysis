/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.ParameterMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link ParameterMode}
 *
 * @author Steve Ebersole
 */
public class ParameterModeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ParameterMode fromXml(String name) {
		return name == null ? null : ParameterMode.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(ParameterMode parameterMode) {
		return parameterMode == null ? null : parameterMode.name();
	}
}
