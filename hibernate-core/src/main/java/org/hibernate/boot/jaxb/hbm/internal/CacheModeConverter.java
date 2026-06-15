/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.internal;

import java.util.Locale;

import org.hibernate.CacheMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class CacheModeConverter {
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public static CacheMode fromXml(String name) {
		for ( CacheMode mode : CacheMode.values() ) {
			if ( mode.name().equalsIgnoreCase( name ) ) {
				return mode;
			}
		}
		return CacheMode.NORMAL;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(CacheMode cacheMode) {
		return cacheMode.name().toLowerCase( Locale.ENGLISH );
	}
}
