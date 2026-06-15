/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.cfg.internal;

import java.util.Comparator;

import org.hibernate.cache.cfg.spi.CollectionDataCachingConfig;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.mapping.Collection;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.type.BasicType;

import static org.hibernate.cache.cfg.internal.ComparatorUtil.versionComparator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class CollectionDataCachingConfigImpl
		extends AbstractDomainDataCachingConfig
		implements CollectionDataCachingConfig {
	private final Collection collectionDescriptor;
	private final NavigableRole navigableRole;

	public CollectionDataCachingConfigImpl(
			Collection collectionDescriptor,
			AccessType accessType) {
		super( accessType );
		this.collectionDescriptor = collectionDescriptor;
		this.navigableRole = new NavigableRole( collectionDescriptor.getRole() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMutable() {
		return collectionDescriptor.isMutable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isVersioned() {
		return collectionDescriptor.getOwner().isVersioned();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Comparator<Object> getOwnerVersionComparator() {
		if ( isVersioned() ) {
			final var type = (BasicType<?>)
					collectionDescriptor.getOwner().getVersion().getType();
			return versionComparator( type );
		}
		else {
			return null;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return navigableRole;
	}
}
