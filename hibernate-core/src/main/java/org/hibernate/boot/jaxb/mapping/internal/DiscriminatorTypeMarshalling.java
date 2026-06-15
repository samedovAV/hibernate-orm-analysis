/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import jakarta.persistence.DiscriminatorType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link DiscriminatorType}
 *
 * @author Steve Ebersole
 */
public class DiscriminatorTypeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static DiscriminatorType fromXml(String name) {
		return name == null ? null : DiscriminatorType.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(DiscriminatorType discriminatorType) {
		return discriminatorType == null ? null : discriminatorType.name();
	}
}
