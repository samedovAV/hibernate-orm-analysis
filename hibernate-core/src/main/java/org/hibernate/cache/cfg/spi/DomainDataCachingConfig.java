/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.cfg.spi;

import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Configuration for a specific type of data to be stored in the
 * region
 *
 * @author Steve Ebersole
 */
public interface DomainDataCachingConfig {
	/**
	 * The requested AccessType
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AccessType getAccessType();

	/**
	 * Is the data marked as being mutable?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	/**
	 * Is the data to be cached considered versioned?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isVersioned();

	/**
	 * The {@link NavigableRole} of the thing to be cached
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigableRole getNavigableRole();
}
