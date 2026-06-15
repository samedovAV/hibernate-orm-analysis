/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.annotation.Nullable;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.metamodel.mapping.EmbeddableMappingType;
import org.hibernate.metamodel.spi.RuntimeModelCreationContext;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.AggregateJdbcType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class PostgreSQLStructCastingJdbcType extends AbstractPostgreSQLStructJdbcType {

	public static final PostgreSQLStructCastingJdbcType INSTANCE = new PostgreSQLStructCastingJdbcType();
	public PostgreSQLStructCastingJdbcType() {
		this( null, null, null );
	}

	private PostgreSQLStructCastingJdbcType(
			EmbeddableMappingType embeddableMappingType,
			String typeName,
			int[] orderMapping) {
		super( embeddableMappingType, typeName, orderMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AggregateJdbcType resolveAggregateJdbcType(
			EmbeddableMappingType mappingType,
			String sqlType,
			RuntimeModelCreationContext creationContext) {
		return new PostgreSQLStructCastingJdbcType(
				mappingType,
				sqlType,
				creationContext.getBootModel()
						.getDatabase()
						.getDefaultNamespace()
						.locateUserDefinedType( Identifier.toIdentifier( sqlType ) )
						.getOrderMapping()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendWriteExpression(
			String writeExpression,
			@Nullable Size size,
			SqlAppender appender,
			Dialect dialect) {
		appender.append( "cast(" );
		appender.append( writeExpression );
		appender.append( " as " );
		appender.append( getStructTypeName() );
		appender.append( ')' );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isWriteExpressionTyped(Dialect dialect) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				final String stringValue = ( (PostgreSQLStructCastingJdbcType) getJdbcType() ).toString(
						value,
						getJavaType(),
						options
				);
				st.setString( index, stringValue );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				final String stringValue = ( (PostgreSQLStructCastingJdbcType) getJdbcType() ).toString(
						value,
						getJavaType(),
						options
				);
				st.setString( name, stringValue );
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public Object getBindValue(X value, WrapperOptions options) throws SQLException {
				return ( (PostgreSQLStructCastingJdbcType) getJdbcType() ).getBindValue( value, options );
			}
		};
	}
}
