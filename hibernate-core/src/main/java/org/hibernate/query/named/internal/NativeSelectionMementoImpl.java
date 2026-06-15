/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named.internal;

import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import jakarta.annotation.Nullable;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.named.NamedNativeQueryMemento;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.query.sql.spi.NativeQueryImplementor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NativeSelectionMementoImpl<R>
		extends AbstractSelectionMemento<R>
		implements NamedNativeQueryMemento<R>, Serializable {
	private final String sqlString;
	private final String resultSetMappingName;
	private final Set<String> synchronizationSpaces;

	public NativeSelectionMementoImpl(
			String name, String sqlString,
			@Nullable Class<R> queryType, String resultSetMappingName, Set<String> synchronizationSpaces,
			FlushMode flushMode, Timeout timeout, String comment,
			Boolean readOnly, Integer fetchSize, Integer firstRow, Integer maxRows,
			Boolean cacheable, CacheMode cacheMode, String cacheRegion,
			LockMode lockMode, PessimisticLockScope lockScope, Timeout lockTimeout, Locking.FollowOn followOnLockingStrategy,
			Map<String, Object> hints) {
		super( name, queryType, flushMode, timeout, comment, readOnly, fetchSize, firstRow, maxRows, cacheable,
				cacheMode,
				cacheRegion, lockMode, lockScope, lockTimeout, followOnLockingStrategy, hints );
		this.sqlString = sqlString;
		this.resultSetMappingName = resultSetMappingName;
		this.synchronizationSpaces = synchronizationSpaces;
	}

	public NativeSelectionMementoImpl(String name, NativeSelectionMementoImpl<R> original) {
		super( name, original );
		this.sqlString = original.sqlString;
		this.resultSetMappingName = original.resultSetMappingName;
		this.synchronizationSpaces = original.synchronizationSpaces;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlString() {
		return sqlString;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOriginalSqlString() {
		return getSqlString();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectionString() {
		return getSqlString();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultMappingName() {
		return resultSetMappingName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getQuerySpaces() {
		return synchronizationSpaces;
	}

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityGraphName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeSelectionMementoImpl<R> makeCopy(String name) {
		return new NativeSelectionMementoImpl<>( name, this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void validate(QueryEngine queryEngine) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NativeQueryImplementor<R> toSelectionQuery(SharedSessionContractImplementor session) {
		return toSelectionQuery( session, null );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> NativeQueryImplementor<X> toSelectionQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		return new NativeQueryImpl<>( this, javaType, null, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryImplementor<R> toQuery(SharedSessionContractImplementor session) {
		return toSelectionQuery( session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> NativeQueryImplementor<T> toQuery(SharedSessionContractImplementor session, String resultSetMapping) {
		//noinspection unchecked,rawtypes
		return new NativeQueryImpl( this, null, resultSetMapping, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> NativeQueryImplementor<X> toQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		return toSelectionQuery( session, javaType );
	}
}
