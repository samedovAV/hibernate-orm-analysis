/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.internal;

import org.hibernate.cache.spi.access.NaturalIdDataAccess;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.NaturalIdMapping;
import org.hibernate.metamodel.model.domain.NavigableRole;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractNaturalIdMapping implements NaturalIdMapping {
	private final EntityMappingType declaringType;
	private final boolean mutable;
	private final NaturalIdDataAccess cachesAccess;

	private final NavigableRole role;

	public AbstractNaturalIdMapping(
			EntityMappingType declaringType,
			boolean mutable) {
		this.declaringType = declaringType;
		this.mutable = mutable;
		this.cachesAccess = declaringType.getEntityPersister().getNaturalIdCacheAccessStrategy();
		this.role = declaringType.getNavigableRole().append( PART_NAME );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getDeclaringType() {
		return declaringType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigableRole getNavigableRole() {
		return role;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isMutable() {
		return mutable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NaturalIdDataAccess getCacheAccess() {
		return cachesAccess;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType findContainingEntityMapping() {
		return declaringType;
	}
}
