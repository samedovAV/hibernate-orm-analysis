/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.internal;


import org.hibernate.CacheMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB marshalling for Hibernate's {@link CacheMode}
 *
 * @author Steve Ebersole
 */
public class CacheModeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static CacheMode fromXml(String name) {
		return name == null ? CacheMode.NORMAL : CacheMode.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(CacheMode cacheMode) {
		return cacheMode == null ? null : cacheMode.name();
	}
}
