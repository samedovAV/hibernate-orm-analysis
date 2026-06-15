/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import org.hibernate.cache.spi.access.AccessType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class CacheAccessTypeConverter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static AccessType fromXml(String name) {
		return AccessType.fromExternalName( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(AccessType accessType) {
		return accessType.getExternalName();
	}
}
