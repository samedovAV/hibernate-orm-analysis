/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi.support;

import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.RegionFactory;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractRegion implements Region {
	private final String name;
	private final RegionFactory regionFactory;

	/**
	 * Constructs an {@link AbstractRegion}.
	 *
	 * @param name - the unqualified region name
	 * @param regionFactory - the region factory
	 */
	public AbstractRegion(String name, RegionFactory regionFactory) {
		this.name = name;
		this.regionFactory = regionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RegionFactory getRegionFactory() {
		return regionFactory;
	}

}
