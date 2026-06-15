/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jakarta.persistence.EnumType;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base implementation of {@link ResultSetAccess}.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractResultSetAccess implements ResultSetAccess {
	private final SharedSessionContractImplementor persistenceContext;
	private ResultSetMetaData resultSetMetaData;

	public AbstractResultSetAccess(SharedSessionContractImplementor persistenceContext) {
		this.persistenceContext = persistenceContext;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract SessionFactoryImplementor getFactory();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SharedSessionContractImplementor getPersistenceContext() {
		return persistenceContext;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private SqlExceptionHelper getSqlExceptionHelper() {
		return getFactory().getJdbcServices().getSqlExceptionHelper();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private Dialect getDialect() {
		return getFactory().getJdbcServices().getDialect();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private ResultSetMetaData getResultSetMetaData() {
		if ( resultSetMetaData == null ) {
			try {
				resultSetMetaData = getResultSet().getMetaData();
			}
			catch (SQLException e) {
				throw getSqlExceptionHelper()
						.convert( e, "Unable to access ResultSetMetaData" );
			}
		}

		return resultSetMetaData;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getColumnCount() {
		try {
			return getResultSetMetaData().getColumnCount();
		}
		catch (SQLException e) {
			throw getSqlExceptionHelper()
					.convert( e, "Unable to access ResultSet column count" );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int resolveColumnPosition(String columnName) {
		try {
			return getResultSet().findColumn( normalizeColumnName( columnName ) );
		}
		catch (SQLException e) {
			throw getSqlExceptionHelper()
					.convert( e, "Unable to find column position by name: " + columnName );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String normalizeColumnName(String columnName) {
		return getFactory().getJdbcServices().getJdbcEnvironment().getIdentifierHelper()
				.toMetaDataObjectName( Identifier.toIdentifier( columnName ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String resolveColumnName(int position) {
		try {
			return getDialect().getColumnAliasExtractor()
					.extractColumnAlias( getResultSetMetaData(), position );
		}
		catch (SQLException e) {
			throw getSqlExceptionHelper()
					.convert( e, "Unable to find column name by position" );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getResultCountEstimate() {
		return -1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <J> BasicType<J> resolveType(int position, JavaType<J> explicitJavaType, TypeConfiguration typeConfiguration) {
		try {
			final var metaData = getResultSetMetaData();
			final var registry = typeConfiguration.getJdbcTypeRegistry();
			final String columnTypeName = metaData.getColumnTypeName( position );
			final int columnType = metaData.getColumnType( position );
			final int scale = metaData.getScale( position );
			final int precision = metaData.getPrecision( position );
			final int displaySize = metaData.getColumnDisplaySize( position );
			final var dialect = getDialect();
			final int length = dialect.resolveSqlTypeLength( columnTypeName, columnType, precision, scale, displaySize );
			final var resolvedJdbcType =
					dialect.resolveSqlTypeDescriptor( columnTypeName, columnType, length, scale, registry );
			final var jdbcType =
					explicitJavaType == null
							? resolvedJdbcType
							: jdbcType( explicitJavaType, resolvedJdbcType, length, precision, scale, typeConfiguration );
			// If there is an explicit JavaType, then prefer its recommended JDBC type
			final var javaType =
					explicitJavaType == null
							? jdbcType.getRecommendedJavaType( length, scale, typeConfiguration )
							: explicitJavaType;
			return typeConfiguration.getBasicTypeRegistry()
					.resolve( (JavaType<J>) javaType, jdbcType );
		}
		catch (SQLException e) {
			throw getSqlExceptionHelper()
					.convert( e, "Unable to determine JDBC type code for ResultSet position " + position );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private <J> JdbcType jdbcType(
			JavaType<J> javaType,
			JdbcType resolvedJdbcType,
			int length, int precision, int scale,
			TypeConfiguration typeConfiguration) {
		return javaType.getRecommendedJdbcType(
				new JdbcTypeIndicators() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public TypeConfiguration getTypeConfiguration() {
						return typeConfiguration;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public long getColumnLength() {
						return length;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public int getColumnPrecision() {
						return precision;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public int getColumnScale() {
						return scale;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public EnumType getEnumeratedType() {
						return resolvedJdbcType.isNumber() ? EnumType.ORDINAL : EnumType.STRING;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public Dialect getDialect() {
						return AbstractResultSetAccess.this.getDialect();
					}
				}
		);
	}
}
