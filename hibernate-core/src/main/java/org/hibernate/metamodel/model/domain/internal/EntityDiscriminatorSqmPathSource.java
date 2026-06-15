/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.query.sqm.tree.domain.SqmEntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SqmPathSource implementation for entity discriminator
 *
 * @author Steve Ebersole
 */
public class EntityDiscriminatorSqmPathSource<D> extends AbstractDiscriminatorSqmPathSource<D> {
	private final SqmEntityDomainType<?> entityDomainType;
	private final EntityMappingType entityMapping;

	public EntityDiscriminatorSqmPathSource(
			SqmDomainType<D> discriminatorValueType,
			SqmEntityDomainType<?> entityDomainType,
			EntityMappingType entityMapping) {
		super( discriminatorValueType );
		this.entityDomainType = entityDomainType;
		this.entityMapping = entityMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityDomainType<?> getEntityDomainType() {
		return entityDomainType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getEntityMapping() {
		return entityMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<D> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		return new EntityDiscriminatorSqmPath<>(
				PathHelper.append( lhs, this, intermediatePathSource ),
				pathModel,
				lhs,
				entityDomainType,
				entityMapping,
				lhs.nodeBuilder()
		);
	}
}
