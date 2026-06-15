/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import jakarta.persistence.Parameter;

import org.hibernate.Incubating;
import org.hibernate.type.BindableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Information about the {@linkplain QueryParameter parameters}
 * of a {@linkplain CommonQueryContract query}.
 *
 * @author Steve Ebersole
 *
 * @see CommonQueryContract#getParameterMetadata()
 */
@Incubating
public interface ParameterMetadata {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
	// General purpose

	/**
	 * The {@link QueryParameter}s representing the parameters of the query,
	 * in no particular well-defined order.
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<QueryParameter<?>> getParameters();

	/**
	 * The total number of registered parameters.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getParameterCount();

	/**
	 * Find the {@linkplain QueryParameter parameter reference} registered
	 * under the given name, if there is one.
	 *
	 * @return The registered match, or {@code null} is there is no match
	 *
	 * @see #getQueryParameter(String)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameter<?> findQueryParameter(String name);

	/**
	 * Get the {@linkplain QueryParameter parameter reference} registered
	 * under the given name.
	 *
	 * @return The registered match. Never {@code null}
	 *
	 * @throws IllegalArgumentException if no parameter is registered under that name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameter<?> getQueryParameter(String name);

	/**
	 * Find the {@linkplain QueryParameter parameter reference} registered
	 * at the given position-label, if there is one.
	 *
	 * @return The registered match, or {@code null} is there is no match
	 *
	 * @see #getQueryParameter(int)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameter<?> findQueryParameter(int positionLabel);

	/**
	 * Get the {@linkplain QueryParameter parameter reference} registered
	 * at the given position-label.
	 *
	 * @return The registered match. Never {@code null}
	 *
	 * @throws IllegalArgumentException if no parameter is registered under that position-label
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameter<?> getQueryParameter(int positionLabel);

	/**
	 * Obtain a {@link QueryParameter} representing the same parameter as the
	 * given JPA-standard {@link Parameter}.
	 *
	 * @apiNote According to the spec, only {@link Parameter} references obtained
	 *          from the provider are valid.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryParameter<P> resolve(Parameter<P> param);

	/**
	 * Get the type of the given parameter.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> BindableType<T> getInferredParameterType(QueryParameter<T> parameter);

	/**
	 * Is this parameter reference registered in this collection?
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean containsReference(QueryParameter<?> parameter);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<? extends QueryParameter<?>> getRegistrations();

	/**
	 * General purpose visitation using functional
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitRegistrations(Consumer<QueryParameter<?>> action);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
	// Named parameters

	/**
	 * Does this parameter set contain any named parameters?
	 *
	 * @return {@code true} if there are named parameters; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasNamedParameters();

	/**
	 * Return the names of all named parameters of the query.
	 *
	 * @return the parameter names
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getNamedParameterNames();


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
	// "positional" parameters

	/**
	 * Does this parameter set contain any positional parameters?
	 *
	 * @return {@code true} if there are positional parameters; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasPositionalParameters();

	/**
	 * Get the position labels of all positional parameters.
	 *
	 * @return the position labels
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<Integer> getOrdinalParameterLabels();
}
