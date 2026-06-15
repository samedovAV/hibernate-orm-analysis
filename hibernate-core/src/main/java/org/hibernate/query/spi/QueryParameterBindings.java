/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.function.BiConsumer;

import org.hibernate.Incubating;
import org.hibernate.cache.spi.QueryKey;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.QueryParameter;
import org.hibernate.query.internal.QueryParameterBindingsImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Manages all the parameter bindings for a particular query.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface QueryParameterBindings {
	/**
	 * Has binding been done for the given parameter.  Handles
	 * cases where we do not (yet) have a binding object as well
	 * by simply returning false.
	 *
	 * @param parameter The parameter to check for a binding
	 *
	 * @return {@code true} if its value has been bound; {@code false}
	 * otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isBound(QueryParameterImplementor<?> parameter);

	/**
	 * Access to the binding via QueryParameter reference
	 *
	 * @param parameter The QueryParameter reference
	 *
	 * @return The binding, or {@code null} if not yet bound
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default <P> QueryParameterBinding<P> getBinding(QueryParameter<P> parameter) {
		return getBinding( (QueryParameterImplementor<P>) parameter );
	}

	/**
	 * Access to the binding via QueryParameter reference
	 *
	 * @param parameter The QueryParameter reference
	 *
	 * @return The binding, or {@code null} if not yet bound
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryParameterBinding<P> getBinding(QueryParameterImplementor<P> parameter);

	/**
	 * Access to the binding via name
	 *
	 * @param name The parameter name
	 *
	 * @return The binding, or {@code null} if not yet bound
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBinding<?> getBinding(String name);

	/**
	 * Access to the binding via position
	 *
	 * @param position The parameter position
	 *
	 * @return The binding, or {@code null} if not yet bound
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBinding<?> getBinding(int position);

	/**
	 * Validate the bindings.  Called just before execution
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAnyMultiValuedBindings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAnyTransientEntityBindings(SharedSessionContractImplementor session);

	/**
	 * Generate a "memento" for these parameter bindings that can be used
	 * in creating a {@link QueryKey}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryKey.ParameterBindingsMemento generateQueryKeyMemento(SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitBindings(BiConsumer<? super QueryParameter<?>, ? super QueryParameterBinding<?>> action);

	QueryKey.ParameterBindingsMemento NO_PARAMETER_BINDING_MEMENTO = new QueryKey.ParameterBindingsMemento(){
	};

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static QueryParameterBindings empty() {
		return QueryParameterBindingsImpl.EMPTY;
	}

}
