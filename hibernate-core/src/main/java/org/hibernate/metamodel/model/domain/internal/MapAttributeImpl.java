/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.model.domain.SimpleDomainType;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.internal.SqmMappingModelHelper;
import org.hibernate.query.sqm.tree.SqmJoinType;
import org.hibernate.query.sqm.tree.domain.SqmMapJoin;
import org.hibernate.query.sqm.tree.domain.SqmMapPersistentAttribute;
import org.hibernate.query.sqm.tree.from.SqmAttributeJoin;
import org.hibernate.query.sqm.tree.from.SqmFrom;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MapAttributeImpl<X, K, V>
		extends AbstractPluralAttribute<X, Map<K, V>, V>
		implements SqmMapPersistentAttribute<X, K, V> {
	private final SqmPathSource<K> keyPathSource;

	public MapAttributeImpl(PluralAttributeBuilder<X, Map<K, V>, V, K> xceBuilder) {
		super( xceBuilder );
		keyPathSource = SqmMappingModelHelper.resolveSqmKeyPathSource(
				xceBuilder.getListIndexOrMapKeyType(),
				BindableType.PLURAL_ATTRIBUTE,
				xceBuilder.isGeneric()
		);
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionType getCollectionType() {
		return CollectionType.MAP;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<K> getKeyJavaType() {
		return keyPathSource.getBindableJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<K> getKeyPathSource() {
		return keyPathSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<K> getIndexPathSource() {
		return getKeyPathSource();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable SqmPathSource<?> findSubPathSource(String name) {
		final CollectionPart.Nature nature = CollectionPart.Nature.fromNameExact( name );
		if ( nature != null ) {
			switch ( nature ) {
				case INDEX:
					return keyPathSource;
				case ELEMENT:
					return getElementPathSource();
			}
		}
		return getElementPathSource().findSubPathSource( name );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public @Nullable SqmPathSource<?> findSubPathSource(String name, boolean includeSubtypes) {
		return CollectionPart.Nature.INDEX.getName().equals( name )
				? keyPathSource
				: super.findSubPathSource( name, includeSubtypes );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<?> getIntermediatePathSource(SqmPathSource<?> pathSource) {
		final String pathName = pathSource.getPathName();
		return pathName.equals( getElementPathSource().getPathName() )
			|| pathName.equals( keyPathSource.getPathName() ) ? null : getElementPathSource();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SimpleDomainType<K> getKeyType() {
		return (SimpleDomainType<K>) keyPathSource.getPathType();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SimpleDomainType<K> getKeyGraphType() {
		return getKeyType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmAttributeJoin<X,V> createSqmJoin(
			SqmFrom<?,X> lhs, SqmJoinType joinType, @Nullable String alias, boolean fetched, SqmCreationState creationState) {
		return new SqmMapJoin<>(
				lhs,
				this,
				alias,
				joinType,
				fetched,
				creationState.getCreationContext().getNodeBuilder()
		);
	}
}
