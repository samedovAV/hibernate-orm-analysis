/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import org.hibernate.annotations.OnDeleteAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class OnDeleteActionMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static OnDeleteAction fromXml(String name) {
		return name == null ? null : OnDeleteAction.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(OnDeleteAction accessType) {
		return accessType == null ? null : accessType.name();
	}
}
