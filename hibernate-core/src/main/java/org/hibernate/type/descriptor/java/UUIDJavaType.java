/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.BytesHelper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link UUID} handling.
 *
 * @see org.hibernate.cfg.AvailableSettings#PREFERRED_UUID_JDBC_TYPE
 *
 * @author Steve Ebersole
 */
public class UUIDJavaType extends AbstractClassJavaType<UUID> {
	public static final UUIDJavaType INSTANCE = new UUIDJavaType();

	public UUIDJavaType() {
		super( UUID.class );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInstance(Object value) {
		return value instanceof UUID;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UUID cast(Object value) {
		return (UUID) value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators context) {
		return context.getJdbcType( context.getPreferredSqlTypeCodeForUuid() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean useObjectEqualsHashCode() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString(UUID value) {
		return ToStringTransformer.INSTANCE.transform( value );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UUID fromString(CharSequence string) {
		return ToStringTransformer.INSTANCE.parse( string.toString() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public long getDefaultSqlLength(Dialect dialect, JdbcType jdbcType) {
		if ( jdbcType.isString() ) {
			return 36L;
		}
		else if ( jdbcType.isBinary() ) {
			return 16L;
		}
		return super.getDefaultSqlLength( dialect, jdbcType );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> X unwrap(UUID value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if ( UUID.class.isAssignableFrom( type ) ) {
			return type.cast( PassThroughTransformer.INSTANCE.transform( value ) );
		}
		if ( String.class.isAssignableFrom( type ) ) {
			return type.cast( ToStringTransformer.INSTANCE.transform( value ) );
		}
		if ( byte[].class.isAssignableFrom( type ) ) {
			return type.cast( ToBytesTransformer.INSTANCE.transform( value ) );
		}
		throw unknownUnwrap( type );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> UUID wrap(X value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}
		if (value instanceof UUID) {
			return PassThroughTransformer.INSTANCE.parse( value );
		}
		if (value instanceof String) {
			return ToStringTransformer.INSTANCE.parse( value );
		}
		if (value instanceof byte[]) {
			return ToBytesTransformer.INSTANCE.parse( value );
		}
		throw unknownWrap( value.getClass() );
	}

	public interface ValueTransformer {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Serializable transform(UUID uuid);
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		UUID parse(Object value);
	}

	public static class PassThroughTransformer implements ValueTransformer {
		public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UUID transform(UUID uuid) {
			return uuid;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UUID parse(Object value) {
			return (UUID)value;
		}
	}

	public static class ToStringTransformer implements ValueTransformer {
		public static final ToStringTransformer INSTANCE = new ToStringTransformer();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String transform(UUID uuid) {
			return uuid.toString();
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UUID parse(Object value) {
			return UUID.fromString( (String) value );
		}
	}

	public static class NoDashesStringTransformer implements ValueTransformer {
		public static final NoDashesStringTransformer INSTANCE = new NoDashesStringTransformer();

		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public String transform(UUID uuid) {
			final String stringForm = ToStringTransformer.INSTANCE.transform( uuid );
			return stringForm.substring( 0, 8 )
					+ stringForm.substring( 9, 13 )
					+ stringForm.substring( 14, 18 )
					+ stringForm.substring( 19, 23 )
					+ stringForm.substring( 24 );
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UUID parse(Object value) {
			final String stringValue = (String) value;
			final String uuidString = stringValue.substring( 0, 8 )
					+ "-"
					+ stringValue.substring( 8, 12 )
					+ "-"
					+ stringValue.substring( 12, 16 )
					+ "-"
					+ stringValue.substring( 16, 20 )
					+ "-"
					+ stringValue.substring( 20 );
			return UUID.fromString( uuidString );
		}
	}

	public static class ToBytesTransformer implements ValueTransformer {
		public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public byte[] transform(UUID uuid) {
			byte[] bytes = new byte[16];
			BytesHelper.fromLong( uuid.getMostSignificantBits(), bytes, 0);
			BytesHelper.fromLong( uuid.getLeastSignificantBits(), bytes, 8 );
			return bytes;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public UUID parse(Object value) {
			byte[] bytea = (byte[]) value;
			return new UUID( BytesHelper.asLong( bytea, 0 ), BytesHelper.asLong( bytea, 8 ) );
		}
	}

}
