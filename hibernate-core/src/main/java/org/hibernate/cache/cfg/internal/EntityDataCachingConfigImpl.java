/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.cfg.internal;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.hibernate.cache.cfg.spi.EntityDataCachingConfig;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EntityDataCachingConfigImpl
		extends AbstractDomainDataCachingConfig
		implements EntityDataCachingConfig {
	private final NavigableRole navigableRole;
	private final Supplier<Comparator<Object>> versionComparatorAccess;
	private final boolean isEntityMutable;

	private final Set<NavigableRole> cachedTypes = new HashSet<>();

	public EntityDataCachingConfigImpl(
			NavigableRole rootEntityName,
			Supplier<Comparator<Object>> versionComparatorAccess,
			boolean isEntityMutable,
			AccessType accessType) {
		super( accessType );
		this.navigableRole = rootEntityName;
		this.versionComparatorAccess = versionComparatorAccess;
		this.isEntityMutable = isEntityMutable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Supplier<Comparator<Object>> getVersionComparatorAccess() {
		return versionComparatorAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return isEntityMutable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVersioned() {
		return getVersionComparatorAccess() != null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return navigableRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<NavigableRole> getCachedTypes() {
		return cachedTypes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addCachedType(NavigableRole typeRole) {
		cachedTypes.add( typeRole );
	}
}
