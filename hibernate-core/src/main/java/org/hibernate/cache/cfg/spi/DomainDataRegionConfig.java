/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.cfg.spi;

import java.util.List;

import org.hibernate.boot.spi.SessionFactoryOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Configuration for a named region for caching domain data.
 * A region's name is "unqualified"; i.e. it is not prefixed by
 * {@link SessionFactoryOptions#getCacheRegionPrefix()}.
 *
 * @author Steve Ebersole
 */
public interface DomainDataRegionConfig {

	/**
	 * Retrieve the unqualified name of this region.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRegionName();

	/**
	 * Retrieve the list of all entity data to be stored in this region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<EntityDataCachingConfig> getEntityCaching();

	/**
	 * Retrieve the list of all natural-id data to be stored in this region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<NaturalIdDataCachingConfig> getNaturalIdCaching();

	/**
	 * Retrieve the list of all collection data to be stored in this region
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<CollectionDataCachingConfig> getCollectionCaching();
}
