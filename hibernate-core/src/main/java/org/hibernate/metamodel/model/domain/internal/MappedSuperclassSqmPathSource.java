/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nullable;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.SqmJoinable;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.domain.SqmEntityValuedSimplePath;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmPluralPartJoin;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import org.hibernate.query.sqm.tree.domain.SqmMappedSuperclassDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MappedSuperclassSqmPathSource<J> extends AbstractSqmPathSource<J> implements SqmJoinable<Object, J> {
	private final boolean isGeneric;
	private final SqmMappedSuperclassDomainType<J> domainType;

	public MappedSuperclassSqmPathSource(
			String localPathName,
			SqmPathSource<J> pathModel,
			SqmMappedSuperclassDomainType<J> domainType,
			BindableType jpaBindableType,
			boolean isGeneric) {
		super( localPathName, pathModel, domainType, jpaBindableType );
		this.isGeneric = isGeneric;
		this.domainType = domainType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmMappedSuperclassDomainType<J> getPathType() {
		return domainType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable SqmPathSource<?> findSubPathSource(String name) {
		return getPathType().findSubPathSource( name );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isGeneric() {
		return isGeneric;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<J> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		return new SqmEntityValuedSimplePath<>(
				PathHelper.append( lhs, this, intermediatePathSource ),
				pathModel,
				lhs,
				lhs.nodeBuilder()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPluralPartJoin<Object, J> createSqmJoin(
			SqmFrom<?, Object> lhs,
			SqmJoinType joinType,
			@Nullable String alias,
			boolean fetched,
			SqmCreationState creationState) {
		return new SqmPluralPartJoin<>(
				lhs,
				pathModel,
				alias,
				joinType,
				creationState.getCreationContext().getNodeBuilder()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return getPathName();
	}
}
