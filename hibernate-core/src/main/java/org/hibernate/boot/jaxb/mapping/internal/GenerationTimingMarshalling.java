/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;

import org.hibernate.boot.jaxb.mapping.GenerationTiming ;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for {@link GenerationTiming}
 *
 * @author Steve Ebersole
 */
public class GenerationTimingMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static GenerationTiming fromXml(String name) {
		return name == null ? null : GenerationTiming.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(GenerationTiming generationTiming) {
		return null == generationTiming ? null : generationTiming.name();
	}
}
