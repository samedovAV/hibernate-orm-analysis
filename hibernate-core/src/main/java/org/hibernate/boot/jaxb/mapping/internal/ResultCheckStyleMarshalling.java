/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import org.hibernate.boot.jaxb.ResultCheckStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshaling for {@link ResultCheckStyle}
 *
 * @author Steve Ebersole
 */
public class ResultCheckStyleMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ResultCheckStyle fromXml(String name) {
		return name == null ? null : ResultCheckStyle.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(ResultCheckStyle style) {
		return style == null ? null : style.name();
	}
}
