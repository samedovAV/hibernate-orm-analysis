/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.InheritanceType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link InheritanceType}
 *
 * @author Steve Ebersole
 */
public class InheritanceTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static InheritanceType fromXml(String name) {
		return name == null ? null : InheritanceType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(InheritanceType inheritanceType) {
		return inheritanceType == null ? null : inheritanceType.name();
	}
}
