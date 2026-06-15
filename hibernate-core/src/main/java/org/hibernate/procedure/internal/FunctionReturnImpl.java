/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.internal;

import java.sql.Types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.procedure.spi.FunctionReturnImplementor;
import org.hibernate.procedure.spi.NamedCallableQueryMemento;
import org.hibernate.procedure.spi.ProcedureCallImplementor;
import org.hibernate.type.BindableType;
import org.hibernate.type.OutputableType;
import org.hibernate.sql.exec.internal.JdbcCallFunctionReturnImpl.RefCurserJdbcCallFunctionReturnImpl;
import org.hibernate.sql.exec.internal.JdbcCallFunctionReturnImpl.RegularJdbcCallFunctionReturnImpl;
import org.hibernate.sql.exec.internal.JdbcCallParameterExtractorImpl;
import org.hibernate.sql.exec.internal.JdbcCallRefCursorExtractorImpl;
import org.hibernate.sql.exec.spi.JdbcCallFunctionReturn;

import jakarta.persistence.ParameterMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class FunctionReturnImpl<T> implements FunctionReturnImplementor<T> {

	private final ProcedureCallImplementor<?> procedureCall;
	private final int sqlTypeCode;
	private final OutputableType<T> ormType;

	FunctionReturnImpl(ProcedureCallImplementor<?> procedureCall, int sqlTypeCode) {
		this.procedureCall = procedureCall;
		this.sqlTypeCode = sqlTypeCode;
		this.ormType = resolveOrmType( procedureCall, sqlTypeCode );
	}

	FunctionReturnImpl(ProcedureCallImplementor<?> procedureCall, OutputableType<T> ormType) {
		this.procedureCall = procedureCall;
		this.sqlTypeCode = ormType.getJdbcType().getDefaultSqlTypeCode();
		this.ormType = ormType;
	}

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static <T> OutputableType<T> resolveOrmType(ProcedureCallImplementor<?> procedureCall, int sqlTypeCode) {
		final var typeConfiguration = procedureCall.getSession().getFactory().getTypeConfiguration();
		final var javaType =
				typeConfiguration.getJdbcTypeRegistry().getDescriptor( sqlTypeCode )
						.getRecommendedJavaType( null, null, typeConfiguration );
		return (OutputableType<T>) typeConfiguration.standardBasicTypeForJavaType( javaType.getJavaTypeClass() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcCallFunctionReturn toJdbcFunctionReturn(SharedSessionContractImplementor persistenceContext) {
		if ( getJdbcTypeCode() == Types.REF_CURSOR ) {
			return new RefCurserJdbcCallFunctionReturnImpl( new JdbcCallRefCursorExtractorImpl( 1 ) );
		}
		else {
			return new RegularJdbcCallFunctionReturnImpl(
					ormType,
					new JdbcCallParameterExtractorImpl<T>(
							procedureCall.getProcedureName(),
							null,
							1,
							ormType
					)
			);
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcTypeCode() {
		return sqlTypeCode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BindableType<T> getHibernateType() {
		return ormType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getPosition() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ParameterMode getMode() {
		return ParameterMode.OUT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<T> getParameterType() {
		return ormType.getJavaType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void disallowMultiValuedBinding() {
		// no-op
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyAnticipatedType(BindableType<?> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean allowsMultiValuedBinding() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedCallableQueryMemento.ParameterMemento toMemento() {
		return session -> new FunctionReturnImpl<>( procedureCall, ormType );
	}
}
