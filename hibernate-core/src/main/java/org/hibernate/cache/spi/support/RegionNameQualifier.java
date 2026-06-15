/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.boot.spi.SessionFactoryOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class RegionNameQualifier {
	/**
	 * Singleton access
	 */
	public static final RegionNameQualifier INSTANCE = new RegionNameQualifier();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String qualify(String regionName, SessionFactoryOptions options) {
		final String prefix = options.getCacheRegionPrefix();
		return prefix == null ? regionName : qualify( prefix, regionName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String qualify(String prefix, String regionName) {
		return regionName.startsWith( prefix + '.' ) ? regionName : prefix + '.' + regionName;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isQualified(String regionName, SessionFactoryOptions options) {
		return isQualified( options.getCacheRegionPrefix(), regionName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isQualified(String prefix, String regionName) {
		return prefix != null && regionName.startsWith( prefix );
	}

	private RegionNameQualifier() {
	}
}
