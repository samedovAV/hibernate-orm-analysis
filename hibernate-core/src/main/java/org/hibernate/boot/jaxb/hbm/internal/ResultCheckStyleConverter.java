/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import org.hibernate.boot.jaxb.ResultCheckStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshaling for the ExecuteUpdateResultCheckStyle enum
 *
 * @author Steve Ebersole
 */
public class ResultCheckStyleConverter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static ResultCheckStyle fromXml(String name) {
		return ResultCheckStyle.fromExternalName( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(ResultCheckStyle style) {
		return style.externalName();
	}
}
