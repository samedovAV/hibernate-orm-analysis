/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.GenerationType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link GenerationType}
 *
 * @author Steve Ebersole
 */
public class GenerationTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static GenerationType fromXml(String name) {
		return name == null ? null : GenerationType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(GenerationType generationType) {
		return generationType == null ? null : generationType.name();
	}
}
