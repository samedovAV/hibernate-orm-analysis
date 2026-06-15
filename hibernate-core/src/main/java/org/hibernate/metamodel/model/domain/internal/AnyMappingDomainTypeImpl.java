/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.mapping.Any;
import org.hibernate.metamodel.model.domain.AnyMappingDomainType;
import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.metamodel.model.domain.SimpleDomainType;
import org.hibernate.metamodel.spi.MappingMetamodelImplementor;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.type.AnyType;
import org.hibernate.type.BasicType;
import org.hibernate.type.MetaType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.internal.ConvertedBasicTypeImpl;

import static jakarta.persistence.metamodel.Type.PersistenceType.ENTITY;
import static org.hibernate.metamodel.mapping.internal.AnyDiscriminatorPart.determineDiscriminatorConverter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class AnyMappingDomainTypeImpl<T> implements AnyMappingDomainType<T>, SqmDomainType<T> {
	private final AnyType anyType;
	private final JavaType<T> baseJtd;
	private final BasicType<Class<?>> anyDiscriminatorType;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AnyMappingDomainTypeImpl(
			Any bootAnyMapping,
			AnyType anyType,
			JavaType<T> baseJtd,
			MappingMetamodelImplementor mappingMetamodel) {
		this.anyType = anyType;
		this.baseJtd = baseJtd;

		final var discriminatorType = (MetaType) anyType.getDiscriminatorType();
		final var discriminatorBaseType = (BasicType) discriminatorType.getBaseType();
		final var navigableRole = resolveNavigableRole( bootAnyMapping );
		anyDiscriminatorType = new ConvertedBasicTypeImpl(
				navigableRole.getFullPath(),
				discriminatorBaseType.getJdbcType(),
				determineDiscriminatorConverter(
						navigableRole,
						discriminatorBaseType,
						bootAnyMapping.getMetaValues(),
						discriminatorType.getImplicitValueStrategy(),
						mappingMetamodel
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmDomainType<T> getSqmType() {
		return this;
	}

//	@Override
//	public Class<T> getJavaType() {
//		return AnyMappingDomainType.super.getJavaType();
//	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTypeName() {
		return baseJtd.getTypeName();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private NavigableRole resolveNavigableRole(Any bootAnyMapping) {
		final var buffer = new StringBuilder();
		final var table = bootAnyMapping.getTable();
		if ( table != null ) {
			buffer.append( table.getName() );
		}

		buffer.append( "(" );
		final var columns = bootAnyMapping.getColumns();
		for ( int i = 0; i < columns.size(); i++ ) {
			buffer.append( columns.get( i ).getName() );
			if ( i+1 < columns.size() ) {
				// still more columns
				buffer.append( "," );
			}
		}
		buffer.append( ")" );

		return new NavigableRole( buffer.toString() );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceType getPersistenceType() {
		return ENTITY;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<T> getJavaType() {
		return baseJtd.getJavaTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<T> getExpressibleJavaType() {
		return baseJtd;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicType<Class<?>> getDiscriminatorType() {
		return anyDiscriminatorType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SimpleDomainType<?> getKeyType() {
		return (BasicType<?>) anyType.getIdentifierType();
	}

}
