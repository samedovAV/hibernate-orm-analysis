/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import org.hibernate.HibernateException;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.type.descriptor.WrapperOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link Class} handling.
 *
 * @author Steve Ebersole
 */
public class ClassJavaType extends AbstractClassJavaType<Class<?>> {
	public static final ClassJavaType INSTANCE = new ClassJavaType();

	@SuppressWarnings({"unchecked", "rawtypes"} )
	public ClassJavaType() {
		super( (Class) Class.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInstance(Object value) {
		return value instanceof Class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> cast(Object value) {
		return (Class<?>) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useObjectEqualsHashCode() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString(Class<?> value) {
		return value.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<?> fromString(CharSequence string) {
		if ( string == null ) {
			return null;
		}

		try {
			return ReflectHelper.classForName( string.toString() );
		}
		catch ( ClassNotFoundException e ) {
			throw new HibernateException( "Unable to locate named class " + string );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(Class<?> value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( Class.class.isAssignableFrom( type ) ) {
			return type.cast( value );
		}
		if ( String.class.isAssignableFrom( type ) ) {
			return type.cast( toString( value ) );
		}
		throw unknownUnwrap( type );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> Class<?> wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if (value instanceof Class) {
			return (Class<?>) value;
		}
		if (value instanceof CharSequence) {
			return fromString( (CharSequence) value );
		}
		throw unknownWrap( value.getClass() );
	}

}
