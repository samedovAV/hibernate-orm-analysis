/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.cfg.internal;

import org.hibernate.internal.util.StringHelper;

import jakarta.persistence.SharedCacheMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SharedCacheModeMarshalling {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static SharedCacheMode fromXml(String name) {
		if ( StringHelper.isEmpty( name ) ) {
			return SharedCacheMode.UNSPECIFIED;
		}
		return SharedCacheMode.valueOf( name );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static String toXml(SharedCacheMode sharedCacheMode) {
		if ( sharedCacheMode == null ) {
			return null;
		}
		return sharedCacheMode.name();
	}
}
