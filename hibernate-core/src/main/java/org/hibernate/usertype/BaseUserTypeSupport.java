/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;

import org.hibernate.HibernateException;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.BasicJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class BaseUserTypeSupport<T> implements UserType<T> {
	private BasicJavaType<T> javaType;
	private JdbcType jdbcType;

	private boolean resolved;

	// cached JDBC extractor and binder
	private ValueExtractor<T> jdbcValueExtractor;
	private ValueBinder<T> jdbcValueBinder;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract void resolve(BiConsumer<BasicJavaType<T>, JdbcType> resolutionConsumer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void ensureResolved() {
		if ( !resolved ) {
			resolve( (javaType, jdbcType) -> {
				this.javaType = javaType;
				this.jdbcType = jdbcType;

				jdbcValueExtractor = jdbcType.getExtractor( javaType );
				jdbcValueBinder = jdbcType.getBinder( javaType );

				resolved = true;
			} );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcType jdbcType() {
		ensureResolved();
		return jdbcType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected BasicJavaType<T> javaType() {
		ensureResolved();
		return javaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getSqlType() {
		ensureResolved();
		return jdbcType.getDdlTypeCode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<T> returnedClass() {
		return javaType().getJavaTypeClass();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean equals(T x, T y) throws HibernateException {
		return javaType().areEqual( x, y );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode(T x) throws HibernateException {
		return javaType().extractHashCode( x );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T nullSafeGet(ResultSet rs, int position, WrapperOptions options) throws SQLException {
		ensureResolved();
		return jdbcValueExtractor.extract( rs, position, options );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void nullSafeSet(PreparedStatement st, T value, int index, WrapperOptions options) throws SQLException {
		ensureResolved();
		jdbcValueBinder.bind( st, value, index, options );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T deepCopy(T value) throws HibernateException {
		return javaType().getMutabilityPlan().deepCopy( value );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isMutable() {
		return javaType().getMutabilityPlan().isMutable();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Serializable disassemble(T value) throws HibernateException {
		return javaType().getMutabilityPlan().disassemble( value, null );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T assemble(Serializable cached, Object owner) throws HibernateException {
		return javaType().getMutabilityPlan().assemble( cached, null );
	}
}
