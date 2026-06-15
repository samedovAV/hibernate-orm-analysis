/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;


import jakarta.annotation.Nullable;
import org.hibernate.Internal;
import org.hibernate.type.BindableType;

import static org.hibernate.query.QueryLogging.QUERY_MESSAGE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base implementation of {@link org.hibernate.query.QueryParameter}.
 *
 * @apiNote This class is now considered internal implementation
 * and will move to an internal package in a future version.
 * Application programs should never depend directly on this class.
 *
 * @author Steve Ebersole
 */
@Internal
public abstract class AbstractQueryParameter<T> implements QueryParameterImplementor<T> {

	private boolean allowMultiValuedBinding;
	private @Nullable BindableType<T> anticipatedType;

	public AbstractQueryParameter(boolean allowMultiValuedBinding, @Nullable BindableType<T> anticipatedType) {
		this.allowMultiValuedBinding = allowMultiValuedBinding;
		this.anticipatedType = anticipatedType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void disallowMultiValuedBinding() {
		QUERY_MESSAGE_LOGGER.debugf( "QueryParameter#disallowMultiValuedBinding() called: %s", this );
		this.allowMultiValuedBinding = false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean allowsMultiValuedBinding() {
		return allowMultiValuedBinding;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable BindableType<T> getHibernateType() {
		return anticipatedType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void applyAnticipatedType(BindableType<?> type) {
		//noinspection unchecked
		this.anticipatedType = (BindableType<T>) type;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getPosition() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<T> getParameterType() {
		return anticipatedType == null ? null : anticipatedType.getJavaType();
	}
}
