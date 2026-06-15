/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.converter.internal;

import java.util.Collection;

import org.hibernate.internal.build.AllowReflection;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.java.spi.BasicCollectionJavaType;

import static java.lang.reflect.Array.newInstance;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Handles conversion to/from a collection of a converted element type.
 */
public class CollectionConverter<X extends Collection<E>, E, R> implements BasicValueConverter<X, R[]> {

	private final BasicValueConverter<E, R> elementConverter;
	private final BasicCollectionJavaType<X, ?> domainJavaType;
	private final JavaType<R[]> relationalJavaType;

	public CollectionConverter(
			BasicValueConverter<E, R> elementConverter,
			BasicCollectionJavaType<X, E> domainJavaType,
			JavaType<R[]> relationalJavaType) {
		this.elementConverter = elementConverter;
		this.domainJavaType = domainJavaType;
		this.relationalJavaType = relationalJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public X toDomainValue(R[] relationalForm) {
		if ( relationalForm == null ) {
			return null;
		}
		final X domainForm =
				domainJavaType.getSemantics()
						.instantiateRaw( relationalForm.length, null );
		for ( R r : relationalForm ) {
			domainForm.add( elementConverter.toDomainValue( r ) );
		}
		return domainForm;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public R[] toRelationalValue(X domainForm) {
		if ( domainForm == null ) {
			return null;
		}
		final R[] relationalArray = newRelationalArray( domainForm.size() );
		int i = 0;
		for ( var domainValue : domainForm ) {
			relationalArray[i++] = elementConverter.toRelationalValue( domainValue );
		}
		return relationalArray;
	}

	@AllowReflection
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private R[] newRelationalArray(int size) {
		final Object result = newInstance( elementConverter.getRelationalJavaType().getJavaTypeClass(), size );
		//noinspection unchecked
		return (R[]) result;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<X> getDomainJavaType() {
		return domainJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<R[]> getRelationalJavaType() {
		return relationalJavaType;
	}

}
