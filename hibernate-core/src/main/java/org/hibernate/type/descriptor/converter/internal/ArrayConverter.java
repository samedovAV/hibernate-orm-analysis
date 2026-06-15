/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.converter.internal;

import org.hibernate.internal.build.AllowReflection;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.converter.spi.BasicValueConverter;
import org.hibernate.type.descriptor.java.JavaType;

import static java.lang.reflect.Array.newInstance;
import static org.hibernate.internal.util.ReflectHelper.arrayClass;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Given a {@link BasicValueConverter} for array elements, handles conversion
 * to and from an array of the converted element type.
 *
 * @param <E> the unconverted element type
 * @param <F> the converted element type
 * @param <T> the unconverted array type (same as {@code E[]})
 */
@AllowReflection
public class ArrayConverter<T, E, F> implements BasicValueConverter<T, F[]> {

	private final BasicValueConverter<E, F> elementConverter;
	private final JavaType<T> domainJavaType;
	private final JavaType<F[]> relationalJavaType;
	private final Class<E[]> arrayClass;

	public ArrayConverter(
			BasicValueConverter<E, F> elementConverter,
			JavaType<T> domainJavaType,
			JavaType<F[]> relationalJavaType,
			BasicType<E> elementType) {
		this.elementConverter = elementConverter;
		this.domainJavaType = domainJavaType;
		this.relationalJavaType = relationalJavaType;
		this.arrayClass = arrayClass( elementType.getJavaType() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T toDomainValue(F[] relationalForm) {
		if ( relationalForm == null ) {
			return null;
		}
		else {
			final var elementClass =
					elementConverter.getDomainJavaType().getJavaTypeClass();
			return relationalForm.getClass().getComponentType() == elementClass
					? domainJavaType.cast( relationalForm )
					: convertTo( relationalJavaType.cast( relationalForm ), elementClass );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private T convertTo(F[] relationalArray, Class<E> elementClass) {
		//TODO: the following implementation only handles conversion between non-primitive arrays!
		final var domainArray =
				arrayClass.cast( newInstance( elementClass, relationalArray.length ) );
		for ( int i = 0; i < relationalArray.length; i++ ) {
			domainArray[i] = elementConverter.toDomainValue( relationalArray[i] );
		}
		return domainJavaType.cast( domainArray );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public F[] toRelationalValue(T domainForm) {
		if ( domainForm == null ) {
			return null;
		}
		else {
			final Class<F> elementClass =
					elementConverter.getRelationalJavaType().getJavaTypeClass();
			return domainForm.getClass().getComponentType() == elementClass
					? relationalJavaType.cast( domainForm )
					: convertFrom( arrayClass.cast( domainForm ), elementClass );
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private F[] convertFrom(E[] domainArray, Class<F> elementClass) {
		//TODO: the following implementation only handles conversion between non-primitive arrays!
		final var relationalArray =
				relationalJavaType.cast( newInstance( elementClass, domainArray.length ) );
		for ( int i = 0; i < domainArray.length; i++ ) {
			relationalArray[i] = elementConverter.toRelationalValue( domainArray[i] );
		}
		return relationalArray;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<T> getDomainJavaType() {
		return domainJavaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<F[]> getRelationalJavaType() {
		return relationalJavaType;
	}

}
