/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.boot.model.relational.Database;
import org.hibernate.engine.jdbc.CharacterStream;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for {@link Types#NCLOB NCLOB} handling.
 *
 * @author Steve Ebersole
 * @author Gail Badner
 * @author Loïc Lefèvre
 */
public abstract class NClobJdbcType implements JdbcType {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return Types.NCLOB;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFriendlyName() {
		return "NCLOB";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "NClobTypeDescriptor";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				if ( options.getDialect().supportsNationalizedMethods() ) {
					return javaType.wrap( rs.getNClob( paramIndex ), options );
				}
				else {
					return javaType.wrap( rs.getClob( paramIndex ), options );
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options)
					throws SQLException {
				if ( options.getDialect().supportsNationalizedMethods() ) {
					return javaType.wrap( statement.getNClob( index ), options );
				}
				else {
					return javaType.wrap( statement.getClob( index ), options );
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
					throws SQLException {
				if ( options.getDialect().supportsNationalizedMethods() ) {
					return javaType.wrap( statement.getNClob( name ), options );
				}
				else {
					return javaType.wrap( statement.getClob( name ), options );
				}
			}
		};
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract <X> BasicBinder<X> getNClobBinder(JavaType<X> javaType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return getNClobBinder( javaType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getExtraCreateTableInfo(JavaType<?> javaType, String columnName, String tableName, Database database) {
		if( javaType.getJavaTypeClass() != Clob.class && javaType.getJavaTypeClass() != NClob.class && database.getDialect().supportsValueLOBAccess() ) {
			return database.getDialect().getValueLOBFragmentForExtraCreateTableInfo(columnName);
		}
		else {
			return JdbcType.super.getExtraCreateTableInfo( javaType, columnName, tableName, database );
		}
	}

	public static final NClobJdbcType DEFAULT = new NClobJdbcType() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "NClobTypeDescriptor(DEFAULT)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return String.class;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		private NClobJdbcType getDescriptor(Object value, WrapperOptions options) {
			if ( value instanceof String ) {
				// performance shortcut for binding CLOB data in String format
				return STRING_BINDING;
			}
			else if ( options.useStreamForLobBinding() ) {
				return STREAM_BINDING;
			}
			else {
				return NCLOB_BINDING;
			}
		}

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					getDescriptor( value, options ).getNClobBinder( javaType ).doBind( st, value, index, options );
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					getDescriptor( value, options ).getNClobBinder( javaType ).doBind( st, value, name, options );
				}
			};
		}
	};

	public static final NClobJdbcType STRING_BINDING = new NClobJdbcType() {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				public String toString() {
			return "NClobTypeDescriptor(STRING_BINDING)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return String.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNString( index, javaType.unwrap( value, String.class, options ) );
					}
					else {
						st.setString( index, javaType.unwrap( value, String.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNString( name, javaType.unwrap( value, String.class, options ) );
					}
					else {
						st.setString( name, javaType.unwrap( value, String.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, index, options );
					}
					else {
						st.setNull( index, Types.CLOB );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, name, options );
					}
					else {
						st.setNull( name, Types.CLOB );
					}
				}
			};
		}
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
			return new BasicExtractor<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( rs.getNString( paramIndex ), options );
					}
					else {
						return javaType.wrap( rs.getString( paramIndex ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNString( index ), options );
					}
					else {
						return javaType.wrap( statement.getString( index ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNString( name ), options );
					}
					else {
						return javaType.wrap( statement.getString( name ), options );
					}
				}
			};
		}
	};

	public static final NClobJdbcType NCLOB_BINDING = new NClobJdbcType() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "NClobTypeDescriptor(NCLOB_BINDING)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return NClob.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNClob( index, javaType.unwrap( value, NClob.class, options ) );
					}
					else {
						st.setClob( index, javaType.unwrap( value, NClob.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNClob( name, javaType.unwrap( value, NClob.class, options ) );
					}
					else {
						st.setClob( name, javaType.unwrap( value, NClob.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, index, options );
					}
					else {
						st.setNull( index, Types.CLOB );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, name, options );
					}
					else {
						st.setNull( name, Types.CLOB );
					}
				}
			};
		}
	};

	public static final NClobJdbcType STREAM_BINDING = new NClobJdbcType() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "NClobTypeDescriptor(STREAM_BINDING)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return CharacterStream.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					final var characterStream = javaType.unwrap( value, CharacterStream.class, options );
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNCharacterStream( index, characterStream.asReader(), characterStream.getLength() );
					}
					else {
						st.setCharacterStream( index, characterStream.asReader(), characterStream.getLength() );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					final var characterStream = javaType.unwrap( value, CharacterStream.class, options );
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNCharacterStream( name, characterStream.asReader(), characterStream.getLength() );
					}
					else {
						st.setCharacterStream( name, characterStream.asReader(), characterStream.getLength() );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, index, options );
					}
					else {
						st.setNull( index, Types.CLOB );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, name, options );
					}
					else {
						st.setNull( name, Types.CLOB );
					}
				}
			};
		}
	};

	public static final NClobJdbcType STREAM_BINDING_EXTRACTING = new NClobJdbcType() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "NClobTypeDescriptor(STREAM_BINDING_EXTRACTING)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return CharacterStream.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					final var characterStream = javaType.unwrap( value, CharacterStream.class, options );
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNCharacterStream( index, characterStream.asReader(), characterStream.getLength() );
					}
					else {
						st.setCharacterStream( index, characterStream.asReader(), characterStream.getLength() );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					final var characterStream = javaType.unwrap( value, CharacterStream.class, options );
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNCharacterStream( name, characterStream.asReader(), characterStream.getLength() );
					}
					else {
						st.setCharacterStream( name, characterStream.asReader(), characterStream.getLength() );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, index, options );
					}
					else {
						st.setNull( index, Types.CLOB );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, name, options );
					}
					else {
						st.setNull( name, Types.CLOB );
					}
				}
			};
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
			return new BasicExtractor<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( rs.getNCharacterStream( paramIndex ), options );
					}
					else {
						return javaType.wrap( rs.getCharacterStream( paramIndex ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNCharacterStream( index ), options );
					}
					else {
						return javaType.wrap( statement.getCharacterStream( index ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNCharacterStream( name ), options );
					}
					else {
						return javaType.wrap( statement.getCharacterStream( name ), options );
					}
				}
			};
		}
	};

	public static final NClobJdbcType MATERIALIZED = new NClobJdbcType() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return "NClobTypeDescriptor(MATERIALIZED)";
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Class<?> getPreferredJavaTypeClass(WrapperOptions options) {
			return String.class;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> BasicBinder<X> getNClobBinder(final JavaType<X> javaType) {
			return new BasicBinder<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNString( index, javaType.unwrap( value, String.class, options ) );
					}
					else {
						st.setString( index, javaType.unwrap( value, String.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						st.setNString( name, javaType.unwrap( value, String.class, options ) );
					}
					else {
						st.setString( name, javaType.unwrap( value, String.class, options ) );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(PreparedStatement st, int index, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, index, options );
					}
					else {
						st.setNull( index, Types.CLOB );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_N, n = "", count = {})
				protected void doBindNull(CallableStatement st, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						super.doBindNull( st, name, options );
					}
					else {
						st.setNull( name, Types.CLOB );
					}
				}
			};
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public <X> ValueExtractor<X> getExtractor(final JavaType<X> javaType) {
			return new BasicExtractor<>( javaType, this ) {
				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( rs.getNString( paramIndex ), options );
					}
					else {
						return javaType.wrap( rs.getString( paramIndex ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, int index, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNString( index ), options );
					}
					else {
						return javaType.wrap( statement.getString( index ), options );
					}
				}

				@Override
				@Prove(complexity = Complexity.O_1, n = "", count = {})
				protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
						throws SQLException {
					if ( options.getDialect().supportsNationalizedMethods() ) {
						return javaType.wrap( statement.getNString( name ), options );
					}
					else {
						return javaType.wrap( statement.getString( name ), options );
					}
				}
			};
		}
	};
}
