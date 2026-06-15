/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.mapping.MappedSuperclass;
import org.hibernate.metamodel.UnsupportedMappingException;
import org.hibernate.metamodel.mapping.EntityIdentifierMapping;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;
import org.hibernate.metamodel.model.domain.spi.JpaMetamodelImplementor;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.domain.SqmMappedSuperclassDomainType;
import org.hibernate.query.sqm.tree.domain.SqmPersistentAttribute;
import org.hibernate.type.descriptor.java.JavaType;

import static jakarta.persistence.metamodel.Bindable.BindableType.ENTITY_TYPE;
import static jakarta.persistence.metamodel.Type.PersistenceType.MAPPED_SUPERCLASS;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of {@link jakarta.persistence.metamodel.MappedSuperclassType}.
 *
 * @author Emmanuel Bernard
 * @author Steve Ebersole
 */
public class MappedSuperclassTypeImpl<J>
		extends AbstractIdentifiableType<J>
		implements SqmMappedSuperclassDomainType<J>, SqmPathSource<J> {

	public MappedSuperclassTypeImpl(
			String name,
			boolean hasIdClass,
			boolean hasIdProperty,
			boolean hasVersion,
			JavaType<J> javaType,
			IdentifiableDomainType<? super J> superType,
			JpaMetamodelImplementor jpaMetamodel) {
		super(
				name,
				javaType,
				superType,
				hasIdClass,
				hasIdProperty,
				hasVersion,
				jpaMetamodel
		);
	}

	public MappedSuperclassTypeImpl(
			JavaType<J> javaType,
			MappedSuperclass mappedSuperclass,
			IdentifiableDomainType<? super J> superType,
			JpaMetamodelImplementor jpaMetamodel) {
		this(
				javaType.getTypeName(),
				mappedSuperclass.getDeclaredIdentifierMapper() != null
						|| superType != null && superType.hasIdClass(),
				mappedSuperclass.hasIdentifierProperty(),
				mappedSuperclass.isVersioned(),
				javaType,
				superType,
				jpaMetamodel
		);
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<J> getBindableJavaType() {
		return getJavaType();
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmDomainType<J> getSqmType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getPathName() {
		return getTypeName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmMappedSuperclassDomainType<J> getPathType() {
		return this;
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathSource<?> findSubPathSource(@Nonnull String name) {
		final var attribute = findAttribute( name );
		if ( attribute != null ) {
			return (SqmPathSource<?>) attribute;
		}
		else if ( "id".equalsIgnoreCase( name ) ) {
			return hasIdClass() ? getIdentifierDescriptor() : null;
		}
		else {
			return null;
		}
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPathSource<?> getIdentifierDescriptor() {
		return super.getIdentifierDescriptor();
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPersistentAttribute<? super J, ?> findAttribute(@Nonnull String name) {
		final var attribute = super.findAttribute( name );
		if ( attribute != null ) {
			return attribute;
		}
		else if ( EntityIdentifierMapping.matchesRoleName( name ) ) {
			return findIdAttribute();
		}
		else {
			return null;
		}
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BindableType getBindableType() {
		return ENTITY_TYPE;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceType getPersistenceType() {
		return MAPPED_SUPERCLASS;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean isIdMappingRequired() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPath<J> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource) {
		throw new UnsupportedMappingException(
				"MappedSuperclassType cannot be used to create an SqmPath - that would be an SqmFrom which are created directly"
		);
	}
}
