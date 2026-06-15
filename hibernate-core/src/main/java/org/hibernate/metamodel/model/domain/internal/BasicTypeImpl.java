/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.internal;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.metamodel.model.domain.BasicDomainType;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Emmanuel Bernard
 */
public class BasicTypeImpl<J> implements BasicDomainType<J>, SqmDomainType<J>, JdbcMapping, Serializable {
	private final JavaType<J> javaType;
	private final JdbcType jdbcType;

	public BasicTypeImpl(JavaType<J> javaType, JdbcType jdbcType) {
		this.javaType = javaType;
		this.jdbcType = jdbcType;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Class<J> getJavaType() {
		return BasicDomainType.super.getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTypeName() {
		return javaType.getTypeName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable SqmDomainType<J> getSqmType() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<J> getExpressibleJavaType() {
		return javaType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean canDoExtraction() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcType getJdbcType() {
		return jdbcType;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public J extract(
			CallableStatement statement,
			int paramIndex,
			SharedSessionContractImplementor session) throws SQLException {
		return jdbcType.getExtractor( javaType ).extract( statement, paramIndex, session );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public J extract(
			CallableStatement statement,
			String paramName,
			SharedSessionContractImplementor session) throws SQLException {
		return jdbcType.getExtractor( javaType ).extract( statement, paramName, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JavaType<?> getJavaTypeDescriptor() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ValueExtractor<J> getJdbcValueExtractor() {
		return jdbcType.getExtractor( javaType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ValueBinder<J> getJdbcValueBinder() {
		return jdbcType.getBinder( javaType );
	}
}
