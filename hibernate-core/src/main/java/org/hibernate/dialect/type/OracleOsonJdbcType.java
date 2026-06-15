/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import oracle.jdbc.OracleType;
import oracle.jdbc.driver.DatabaseError;
import oracle.sql.json.OracleJsonDatum;
import oracle.sql.json.OracleJsonFactory;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.AggregateJdbcType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JsonHelper;
import org.hibernate.type.descriptor.jdbc.spi.JsonGeneratingVisitor;
import org.hibernate.type.format.OsonDocumentReader;
import org.hibernate.type.format.OsonDocumentWriter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Type mapping JSON SQL data type for Oracle database.
 * This implementation is used when the JDBC OSON extension is available.
 *
 * @author Emmanuel Jannetti
 */
public class OracleOsonJdbcType extends OracleJsonJdbcType {
	public static final OracleOsonJdbcType INSTANCE = new OracleOsonJdbcType( null );

	static final OracleJsonFactory OSON_JSON_FACTORY = new OracleJsonFactory();

	private OracleOsonJdbcType(EmbeddableMappingType embeddableMappingType) {
		super( embeddableMappingType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "OracleOsonJdbcType";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AggregateJdbcType resolveAggregateJdbcType(
			EmbeddableMappingType mappingType,
			String sqlType,
			RuntimeModelCreationContext creationContext) {
		return new OracleOsonJdbcType( mappingType );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {

		if ( javaType.getJavaType() == String.class || javaType.getJavaType() == Object.class ) {
			return super.getBinder( javaType );
		}

		return new BasicBinder<>( javaType, this ) {

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private <T> byte[] toOson(T value, JavaType<T> javaType, WrapperOptions options) throws Exception {
				final var out = new ByteArrayOutputStream();
				if ( getEmbeddableMappingType() != null ) {
					// OracleJsonFactory is used and not OracleOsonFactory as Jackson is not involved here
					try ( var generator = OSON_JSON_FACTORY.createJsonBinaryGenerator( out ) ) {
						JsonGeneratingVisitor.INSTANCE.visit(
								getEmbeddableMappingType(),
								value,
								options,
								new OsonDocumentWriter( generator )
						);
					}
				}
				else {
					try ( var osonGen = OracleOsonJacksonHelper.createWriteTarget( out ) ) {
						options.getJsonFormatMapper().writeToTarget( value, javaType, osonGen, options );
					}
				}
				return out.toByteArray();
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private boolean useUtf8(WrapperOptions options) {
				return getEmbeddableMappingType() == null
					&& !options.getJsonFormatMapper().supportsTargetType( OracleOsonJacksonHelper.WRITER_CLASS );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				try {
					if ( useUtf8( options ) ) {
						final String json = OracleOsonJdbcType.this.toString(
								value,
								getJavaType(),
								options
						);
						st.setBytes( index, json.getBytes( StandardCharsets.UTF_8 ) );
					}
					else {
						st.setObject( index, toOson( value, getJavaType(), options ), OracleType.JSON );
					}
				}
				catch (Exception e) {
					throw new SQLException( e );
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				try {
					if ( useUtf8( options ) ) {
						final String json = OracleOsonJdbcType.this.toString(
								value,
								getJavaType(),
								options
						);
						st.setBytes( name, json.getBytes( StandardCharsets.UTF_8 ) );
					}
					else {
						st.setObject( name, toOson( value, getJavaType(), options ), OracleType.JSON );
					}
				}
				catch (Exception e) {
					throw new SQLException( e );
				}
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(JavaType<X> javaType) {

		if ( javaType.getJavaType() == String.class || javaType.getJavaType() == Object.class ) {
			return super.getExtractor( javaType );
		}

		return new BasicExtractor<>( javaType, this ) {

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private X fromOson(InputStream osonBytes, WrapperOptions options) throws Exception {
				if ( getEmbeddableMappingType() != null ) {
					return JsonHelper.deserialize(
							getEmbeddableMappingType(),
							new OsonDocumentReader( OSON_JSON_FACTORY.createJsonBinaryParser( osonBytes ) ),
							javaType.getJavaTypeClass() != Object[].class,
							options
					);
				}
				else {
					try ( var osonParser = OracleOsonJacksonHelper.createReadSource( osonBytes ) ) {
						return options.getJsonFormatMapper().readFromSource( getJavaType(), osonParser, options );
					}
				}
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private boolean useUtf8(WrapperOptions options) {
				return getEmbeddableMappingType() == null
					&& !options.getJsonFormatMapper().supportsSourceType( OracleOsonJacksonHelper.READER_CLASS );
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private X doExtraction(OracleJsonDatum datum, WrapperOptions options) throws SQLException {
				if ( datum == null ) {
					return null;
				}
				final var osonBytes = datum.getStream();
				try {
					return fromOson( osonBytes, options );
				}
				catch (Exception e) {
					throw new SQLException( e );
				}
			}

			@Prove(complexity = Complexity.O_N, n = "", count = {})
			private X fromString(byte[] json, WrapperOptions options) throws SQLException {
				if ( json == null ) {
					return null;
				}
				return OracleOsonJdbcType.this.fromString(
						new String( json, StandardCharsets.UTF_8 ),
						getJavaType(),
						options
				);
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private byte[] getBytesFromResultSetByIndex(ResultSet rs, int index) throws SQLException {
				// This can be a BLOB or a CLOB. getBytes is not supported on CLOB
				// and getString is not supported on BLOB. W have to try both
				try {
					return rs.getBytes( index );
				}
				catch (SQLFeatureNotSupportedException nse) {
					return rs.getString( index ).getBytes();
				}
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private byte[] getBytesFromStatementByIndex(CallableStatement st, int index) throws SQLException {
				// This can be a BLOB or a CLOB. getBytes is not supported on CLOB
				// and getString is not supported on BLOB. W have to try both
				try {
					return st.getBytes( index );
				}
				catch (SQLFeatureNotSupportedException nse) {

					return st.getString( index ).getBytes();
				}
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private byte[] getBytesFromStatementByName(CallableStatement st, String columnName) throws SQLException {
				// This can be a BLOB or a CLOB. getBytes is not supported on CLOB
				// and getString is not supported on BLOB. W have to try both
				try {
					return st.getBytes( columnName );
				}
				catch (SQLFeatureNotSupportedException nse) {
					return st.getString( columnName ).getBytes();
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				if ( useUtf8( options ) ) {
					return fromString( getBytesFromResultSetByIndex( rs, paramIndex ), options );
				}
				else {
					try {
						final var ojd = rs.getObject( paramIndex, OracleJsonDatum.class );
						return doExtraction( ojd, options );
					}
					catch (SQLException exc) {
						if ( exc.getErrorCode() == DatabaseError.JDBC_ERROR_BASE + DatabaseError.EOJ_INVALID_COLUMN_TYPE ) {
							// This may happen if we are fetching data from an existing schema
							// that uses BLOB for JSON column. In that case we assume bytes are
							// UTF-8 bytes (i.e not OSON) and we fall back to previous String-based implementation
							CORE_LOGGER.invalidJSONColumnType( OracleType.BLOB.getName(), OracleType.JSON.getName() );
							return fromString( getBytesFromResultSetByIndex( rs, paramIndex ), options );
						}
						else {
							throw exc;
						}
					}
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				if ( useUtf8( options ) ) {
					return fromString( getBytesFromStatementByIndex( statement, index ), options );
				}
				else {
					try {
						final var ojd = statement.getObject( index, OracleJsonDatum.class );
						return doExtraction( ojd, options );
					}
					catch (SQLException exc) {
						if ( exc.getErrorCode() == DatabaseError.JDBC_ERROR_BASE + DatabaseError.EOJ_INVALID_COLUMN_TYPE ) {
							// This may happen if we are fetching data from an existing schema
							// that uses BLOB for JSON column In that case we assume bytes are
							// UTF-8 bytes (i.e not OSON) and we fall back to previous String-based implementation
							CORE_LOGGER.invalidJSONColumnType( OracleType.CLOB.getName(), OracleType.JSON.getName() );
							return fromString( getBytesFromStatementByIndex( statement, index ), options );
						}
						else {
							throw exc;
						}
					}
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
					throws SQLException {
				if ( useUtf8( options ) ) {
					return fromString( getBytesFromStatementByName( statement, name ), options );
				}
				else {
					try {
						final var ojd = statement.getObject( name, OracleJsonDatum.class );
						return doExtraction( ojd, options );
					}
					catch (SQLException exc) {
						if ( exc.getErrorCode() == DatabaseError.JDBC_ERROR_BASE + DatabaseError.EOJ_INVALID_COLUMN_TYPE ) {
							// This may happen if we are fetching data from an existing schema
							// that uses BLOB for JSON column In that case we assume bytes are
							// UTF-8 bytes (i.e not OSON) and we fall back to previous String-based implementation
							CORE_LOGGER.invalidJSONColumnType( OracleType.CLOB.getName(), OracleType.JSON.getName() );
							return fromString( getBytesFromStatementByName( statement, name ), options );
						}
						else {
							throw exc;
						}
					}
				}
			}
		};
	}
}
