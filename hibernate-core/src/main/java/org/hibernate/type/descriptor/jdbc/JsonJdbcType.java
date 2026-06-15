/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.jdbc;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.spi.JsonGeneratingVisitor;
import org.hibernate.type.format.StringJsonDocumentReader;
import org.hibernate.type.format.StringJsonDocumentWriter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized type mapping for {@code JSON} and the JSON SQL data type.
 *
 * @author Christian Beikov
 * @author Emmanuel Jannetti
 */
public class JsonJdbcType implements AggregateJdbcType {
	/**
	 * Singleton access
	 */
	public static final JsonJdbcType INSTANCE = new JsonJdbcType( null );

	private final EmbeddableMappingType embeddableMappingType;

	protected JsonJdbcType(EmbeddableMappingType embeddableMappingType) {
		this.embeddableMappingType = embeddableMappingType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return SqlTypes.VARCHAR;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.JSON;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "JsonJdbcType";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		// No literal support for now
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AggregateJdbcType resolveAggregateJdbcType(
			EmbeddableMappingType mappingType,
			String sqlType,
			RuntimeModelCreationContext creationContext) {
		return new JsonJdbcType( mappingType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableMappingType getEmbeddableMappingType() {
		return embeddableMappingType;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected <X> X fromString(String string, JavaType<X> javaType, WrapperOptions options)
			throws SQLException {
		if ( string == null ) {
			return null;
		}
		if ( embeddableMappingType != null ) {
			return JsonHelper.deserialize(
					embeddableMappingType,
					new StringJsonDocumentReader(string),
					javaType.getJavaTypeClass() != Object[].class,
					options
			);
		}
		return options.getJsonFormatMapper().fromString( string, javaType, options );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object createJdbcValue(Object domainValue, WrapperOptions options)
			throws SQLException {
		assert embeddableMappingType != null;
		final var writer = new StringJsonDocumentWriter();
		try {
			JsonGeneratingVisitor.INSTANCE.visit( embeddableMappingType, domainValue, options, writer );
			return writer.getJson();
		}
		catch (IOException e) {
			throw new SQLException( e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] extractJdbcValues(Object rawJdbcValue, WrapperOptions options)
			throws SQLException {
		assert embeddableMappingType != null;
		return JsonHelper.deserialize( embeddableMappingType,
				new StringJsonDocumentReader( (String) rawJdbcValue ), false, options );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected <X> String toString(X value, JavaType<X> javaType, WrapperOptions options) {
		if ( embeddableMappingType != null ) {
			try {
				final var writer = new StringJsonDocumentWriter();
				JsonGeneratingVisitor.INSTANCE.visit( embeddableMappingType, value, options, writer );
				return writer.getJson();
			}
			catch (IOException e) {
				throw new RuntimeException("Failed to serialize JSON mapping", e );
			}
		}
		return options.getJsonFormatMapper().toString( value, javaType, options );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				st.setString( index, JsonJdbcType.this.toString( value, getJavaType(), options ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setString( name, JsonJdbcType.this.toString( value, getJavaType(), options ) );
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options)
					throws SQLException {
				return fromString( rs.getString( paramIndex ), getJavaType(), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options)
					throws SQLException {
				return fromString( statement.getString( index ), getJavaType(), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options)
					throws SQLException {
				return fromString( statement.getString( name ), getJavaType(), options );
			}

		};
	}
}
