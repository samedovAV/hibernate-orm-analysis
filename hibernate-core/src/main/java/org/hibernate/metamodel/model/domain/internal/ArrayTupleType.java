/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.cache.MutableCacheKeyBuilder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.internal.util.IndexedConsumer;
import org.hibernate.metamodel.UnsupportedMappingException;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.query.sqm.tuple.TupleType;
import org.hibernate.query.sqm.SqmExpressible;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.ObjectArrayJavaType;

import static jakarta.persistence.metamodel.Type.PersistenceType.EMBEDDABLE;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class ArrayTupleType
		implements TupleType<Object[]>, SqmDomainType<Object[]>, MappingModelExpressible<Object[]> {

	private final ObjectArrayJavaType javaType;
	private final SqmBindableType<?>[] components;

	public ArrayTupleType(SqmBindableType<?>[] components) {
		this.components = components;
		this.javaType = new ObjectArrayJavaType( getTypeDescriptors( components ) );
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Class<Object[]> getJavaType() {
		return TupleType.super.getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTypeName() {
		return SqmDomainType.super.getTypeName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmDomainType<Object[]> getSqmType() {
		return this;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private static JavaType<?>[] getTypeDescriptors(SqmExpressible<?>[] components) {
		final var typeDescriptors = new JavaType<?>[components.length];
		for ( int i = 0; i < components.length; i++ ) {
			typeDescriptors[i] = components[i].getExpressibleJavaType();
		}
		return typeDescriptors;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int componentCount() {
		return components.length;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComponentName(int index) {
		throw new UnsupportedMappingException( "Array tuple has no component names" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getComponentNames() {
		throw new UnsupportedMappingException( "Array tuple has no component names" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmBindableType<?> get(int index) {
		return components[index];
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmBindableType<?> get(String componentName) {
		throw new UnsupportedMappingException( "Array tuple has no component names" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<Object[]> getExpressibleJavaType() {
		return javaType;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceType getPersistenceType() {
		return EMBEDDABLE;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String toString() {
		return "ArrayTupleType" + Arrays.toString( components );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object disassemble(Object value, SharedSessionContractImplementor session) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addToCacheKey(MutableCacheKeyBuilder cacheKey, Object value, SharedSessionContractImplementor session) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X, Y> int forEachDisassembledJdbcValue(
			Object value,
			int offset,
			X x,
			Y y,
			JdbcValuesBiConsumer<X, Y> valuesConsumer,
			SharedSessionContractImplementor session) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int forEachJdbcType(int offset, IndexedConsumer<JdbcMapping> action) {
		throw new UnsupportedOperationException();
	}
}
