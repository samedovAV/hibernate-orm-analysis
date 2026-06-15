/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import java.util.Comparator;
import java.util.TimeZone;

import org.hibernate.type.descriptor.WrapperOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link TimeZone} handling.
 *
 * @author Steve Ebersole
 */
public class TimeZoneJavaType extends AbstractClassJavaType<TimeZone> {
	public static final TimeZoneJavaType INSTANCE = new TimeZoneJavaType();

	public static class TimeZoneComparator implements Comparator<TimeZone> {
		public static final TimeZoneComparator INSTANCE = new TimeZoneComparator();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public int compare(TimeZone o1, TimeZone o2) {
			return o1.getID().compareTo( o2.getID() );
		}
	}

	public TimeZoneJavaType() {
		super( TimeZone.class, ImmutableMutabilityPlan.instance(), TimeZoneComparator.INSTANCE );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInstance(Object value) {
		return value instanceof TimeZone;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimeZone cast(Object value) {
		return (TimeZone) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useObjectEqualsHashCode() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString(TimeZone value) {
		return value.getID();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimeZone fromString(CharSequence string) {
		return TimeZone.getTimeZone( string.toString() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(TimeZone value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( TimeZone.class.isAssignableFrom( type ) ) {
			return type.cast( value );
		}
		if ( String.class.isAssignableFrom( type ) ) {
			return type.cast( toString( value ) );
		}
		throw unknownUnwrap( type );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> TimeZone wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( value instanceof TimeZone ) {
			return (TimeZone) value;
		}
		if ( value instanceof CharSequence ) {
			return fromString( (CharSequence) value );
		}
		throw unknownWrap( value.getClass() );
	}

}
