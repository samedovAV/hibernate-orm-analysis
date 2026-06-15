/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.type;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.annotation.Nullable;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.Size;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.JdbcMappingContainer;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.expression.SelfRenderingExpression;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.AdjustableJdbcType;
import org.hibernate.type.descriptor.jdbc.BasicBinder;
import org.hibernate.type.descriptor.jdbc.BasicExtractor;
import org.hibernate.type.descriptor.jdbc.JdbcLiteralFormatter;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class PostgreSQLCastingIntervalSecondJdbcType implements AdjustableJdbcType {

	public static final PostgreSQLCastingIntervalSecondJdbcType INSTANCE = new PostgreSQLCastingIntervalSecondJdbcType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType resolveIndicatedType(JdbcTypeIndicators indicators, JavaType<?> domainJtd) {
		final int scale = indicators.getColumnScale() == JdbcTypeIndicators.NO_COLUMN_SCALE
				? domainJtd.getDefaultSqlScale( indicators.getDialect(), this )
				: indicators.getColumnScale();
		if ( scale > 6 ) {
			// Since the maximum allowed scale on PostgreSQL is 6 (microsecond precision),
			// we have to switch to the numeric type if the value is greater
			return indicators.getTypeConfiguration().getJdbcTypeRegistry().getDescriptor( SqlTypes.NUMERIC );
		}
		else {
			return this;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Expression wrapTopLevelSelectionExpression(Expression expression) {
		return new SelfRenderingExpression() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public void renderToSql(
					SqlAppender sqlAppender,
					SqlAstTranslator<?> walker,
					SessionFactoryImplementor sessionFactory) {
				sqlAppender.append( "extract(epoch from " );
				expression.accept( walker );
				sqlAppender.append( ')' );
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public JdbcMappingContainer getExpressionType() {
				return expression.getExpressionType();
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void appendWriteExpression(
			String writeExpression,
			@Nullable Size size,
			SqlAppender appender,
			Dialect dialect) {
		appender.append( '(' );
		appender.append( writeExpression );
		appender.append( "*interval'1 second')" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isWriteExpressionTyped(Dialect dialect) {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return SqlTypes.NUMERIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultSqlTypeCode() {
		return SqlTypes.INTERVAL_SECOND;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "IntervalSecondJdbcType";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> JdbcLiteralFormatter<T> getJdbcLiteralFormatter(JavaType<T> javaType) {
		// No literal support for now
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueBinder<X> getBinder(JavaType<X> javaType) {
		return new BasicBinder<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
					throws SQLException {
				st.setBigDecimal( index, getBigDecimalValue( value, options ) );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
					throws SQLException {
				st.setBigDecimal( name, getBigDecimalValue( value, options ) );
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private BigDecimal getBigDecimalValue(X value, WrapperOptions options) {
				return getJavaType().unwrap( value, BigDecimal.class, options ).movePointLeft( 9 );
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> ValueExtractor<X> getExtractor(JavaType<X> javaType) {
		return new BasicExtractor<>( javaType, this ) {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
				return getObject( rs.getBigDecimal( paramIndex ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
				return getObject( statement.getBigDecimal( index ), options );
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
				return getObject( statement.getBigDecimal( name ), options );
			}

			@Prove(complexity = Complexity.O_1, n = "", count = {})
			private X getObject(BigDecimal bigDecimal, WrapperOptions options) throws SQLException {
				if ( bigDecimal == null ) {
					return null;
				}
				return getJavaType().wrap( bigDecimal.movePointRight( 9 ), options );
			}
		};
	}
}
