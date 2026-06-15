/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import org.hibernate.metamodel.RepresentationMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class RepresentationModeConverter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static RepresentationMode fromXml(String name) {
		return RepresentationMode.fromExternalName( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(RepresentationMode entityMode) {
		return ( null == entityMode ) ? null : entityMode.getExternalName();
	}
}
