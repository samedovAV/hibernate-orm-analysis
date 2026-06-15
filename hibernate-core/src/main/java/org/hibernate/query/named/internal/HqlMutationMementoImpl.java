/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named.internal;

import jakarta.persistence.Timeout;
import jakarta.annotation.Nullable;
import org.hibernate.FlushMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.IllegalSelectQueryException;
import org.hibernate.query.internal.MutationQueryImpl;
import org.hibernate.query.internal.QueryHelper;
import org.hibernate.query.named.NamedSqmQueryMemento;
import org.hibernate.query.spi.HqlInterpretation;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.spi.SelectionQueryImplementor;
import org.hibernate.query.sqm.tree.SqmStatement;

import java.io.Serializable;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class HqlMutationMementoImpl<T>
		extends AbstractQueryMemento<T>
		implements SqmMutationMemento<T>, Serializable {
	private final String hqlString;
	private final Map<String, String> parameterTypes;

	public HqlMutationMementoImpl(
			String name, String hqlString, @Nullable Class<T> targetType,
			Map<String, String> parameterTypes,
			FlushMode flushMode, Timeout timeout, String comment,
			Map<String, Object> hints) {
		super( name, targetType, flushMode, timeout, comment, hints );
		this.hqlString = hqlString;
		this.parameterTypes = parameterTypes;
	}

	public HqlMutationMementoImpl(String name, HqlMutationMementoImpl<T> original) {
		super( name, original );
		this.hqlString = original.hqlString;
		this.parameterTypes = original.parameterTypes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getHqlString() {
		return hqlString;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmStatement<T> getSqmStatement() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, String> getAnticipatedParameterTypes() {
		return parameterTypes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedSqmQueryMemento<T> makeCopy(String name) {
		return new HqlMutationMementoImpl<>( name, this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void validate(QueryEngine queryEngine) {
		final var interpretationCache = queryEngine.getInterpretationCache();
		interpretationCache.resolveHqlInterpretation( hqlString, queryType, queryEngine.getHqlTranslator() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public MutationQueryImplementor<T> toMutationQuery(SharedSessionContractImplementor session) {
		return toMutationQuery( session, queryType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> MutationQueryImplementor<X> toMutationQuery(SharedSessionContractImplementor session, Class<X> targetType) {
		final HqlInterpretation<X> interpretation = QueryHelper.interpretation( this, targetType, session );
		return new MutationQueryImpl<>( this, interpretation, targetType, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryImplementor<T> toQuery(SharedSessionContractImplementor session) {
		return toMutationQuery( session, queryType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> QueryImplementor<X> toQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		return toMutationQuery( session, javaType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SelectionQueryImplementor<T> toSelectionQuery(SharedSessionContractImplementor session) {
		throw new IllegalSelectQueryException( "Not a NamedSelectionMemento", hqlString );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <X> SelectionQueryImplementor<X> toSelectionQuery(SharedSessionContractImplementor session, Class<X> javaType) {
		throw new IllegalSelectQueryException( "Not a NamedSelectionMemento", hqlString );
	}
}
