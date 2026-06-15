/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for a named cache "region".  A logical region might not
 * necessarily correlate to any specific concept in the underlying
 * caching provider - it's just a thing that can be referenced by
 * name later.
 * <p>
 * A region's name is "unqualified"; i.e. it is not prefixed by
 * {@link SessionFactoryOptions#getCacheRegionPrefix()}.
 * <p>
 * {@code Region} is the base contract defining some common
 * characteristics regardless of the type of data intended to be
 * stored within the region.  The more specific subtypes are:
 * <ul>
 * <li>{@link DomainDataRegion} for storing entity, collection and
 *     natural-id data and
 * <li>{@link DirectAccessRegion} for storing query result and
 *     timestamp data.
 * </ul>
 *
 * @author Steve Ebersole
 */
public interface Region {
	/**
	 * Retrieve the unqualified name of this region.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * The RegionFactory that generated this Region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RegionFactory getRegionFactory();

	/**
	 * Clear all data cached in the region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void clear();

	/**
	 * The "end state" contract of the region's lifecycle.  Called
	 * during {@link org.hibernate.SessionFactory#close()} to give
	 * the region a chance to cleanup.
	 *
	 * @throws CacheException Indicates problem shutting down
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void destroy() throws CacheException;
}
