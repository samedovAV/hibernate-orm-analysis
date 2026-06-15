/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import jakarta.persistence.Timeout;
import org.hibernate.FlushMode;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.JpaReference;
import org.hibernate.query.spi.MutationQueryImplementor;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.spi.QueryParameterImplementor;
import org.hibernate.query.spi.SelectionQueryImplementor;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// The runtime representation of named queries.  They are stored in and
/// available through the QueryEngine's [NamedObjectRepository].
/// This is the base contract for all specific types of named query mementos
///
/// @author Steve Ebersole
public interface NamedQueryMemento<T> extends JpaReference {
	/**
	 * The name under which the query is registered
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRegistrationName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FlushMode getFlushMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Timeout getTimeout();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, Object> getHints();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validate(QueryEngine queryEngine);

	/**
	 * Makes a copy of the memento using the specified registration name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedQueryMemento<T> makeCopy(String name);

	/// Create a [selection queries][SelectionQueryImplementor] based on this reference's definition.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SelectionQueryImplementor<T> toSelectionQuery(SharedSessionContractImplementor session);

	/// Create a [selection queries][SelectionQueryImplementor] based on this reference's definition with the give result type.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> SelectionQueryImplementor<X> toSelectionQuery(SharedSessionContractImplementor session, Class<X> javaType);

	/// Create a [mutation queries][MutationQueryImplementor] based on this memento's definition.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutationQueryImplementor<T> toMutationQuery(SharedSessionContractImplementor session);

	/// Create a [mutation queries][MutationQueryImplementor] based on this memento's definition.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> MutationQueryImplementor<X> toMutationQuery(SharedSessionContractImplementor session, Class<X> targetType);

	/// Create a [QueryImplementor] reference.  Used in cases where we do not know
	/// up front if we have a selection or mutation query.
	///
	/// @see #toSelectionQuery
	/// @see #toMutationQuery
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryImplementor<T> toQuery(SharedSessionContractImplementor session);

	/// Create a [QueryImplementor] reference.  Used in cases where we do not know
	/// up front if we have a selection or mutation query.
	///
	/// @see #toSelectionQuery
	/// @see #toMutationQuery
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> QueryImplementor<X> toQuery(SharedSessionContractImplementor session, Class<X> javaType);

	interface ParameterMemento {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		QueryParameterImplementor<?> resolve(SharedSessionContractImplementor session);
	}
}
