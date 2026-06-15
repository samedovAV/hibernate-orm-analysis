/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.cfg.internal;

import org.hibernate.cache.cfg.spi.NaturalIdDataCachingConfig;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NaturalIdDataCachingConfigImpl
		extends AbstractDomainDataCachingConfig
		implements NaturalIdDataCachingConfig {
	private final RootClass rootEntityDescriptor;
	private final NavigableRole navigableRole;
	private final boolean mutable;

	public NaturalIdDataCachingConfigImpl(
			RootClass rootEntityDescriptor,
			AccessType accessType) {
		super( accessType );
		this.rootEntityDescriptor = rootEntityDescriptor;
		this.navigableRole = new NavigableRole( rootEntityDescriptor.getEntityName() );
		// Sucks that we need to do this here. Persister does the same "calculation"
		this.mutable = hasAnyMutableNaturalIdProps();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean hasAnyMutableNaturalIdProps() {
		for ( Property property : rootEntityDescriptor.getDeclaredProperties() ) {
			if ( property.isNaturalIdentifier() && property.isUpdatable() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return navigableRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return mutable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isVersioned() {
		return false;
	}
}
