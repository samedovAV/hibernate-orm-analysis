/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.Timeout;
import org.hibernate.LockMode;
import org.hibernate.Locking;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Loader for [org.hibernate.annotations.NaturalId]
///
/// @author Steve Ebersole
public interface NaturalIdLoader<T> extends EntityLoader, MultiKeyLoader {
	interface Options {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		LockMode getLockMode();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Timeout getLockTimeout();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		PessimisticLockScope getLockScope();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Locking.FollowOn getLockFollowOn();
	}

	/// Perform the load of the entity by its natural-id
	///
	/// @param naturalIdToLoad The natural-id to load.  One of 2 forms accepted:
	///		* Single-value - valid for entities with a simple (single-valued)
	///			natural-id
	///		* Map - valid for any natural-id load.  The map is each value keyed
	///			by the attribute name that the value corresponds to.  Even though
	///			this form is allowed for simple natural-ids, the single value form
	///			should be used as it is more efficient
	/// @param options The options to apply to the load operation
	/// @param session The session into which the entity is being loaded
	///
	/// @deprecated (since 7.3) : use [#load(Object, Options, SharedSessionContractImplementor)] instead.
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T load(Object naturalIdToLoad, NaturalIdLoadOptions options, SharedSessionContractImplementor session);

	/// Perform the load of the entity by its natural-id
	///
	/// @param naturalIdToLoad The [normalized][org.hibernate.metamodel.mapping.NaturalIdMapping#normalizeInput]
	/// 	form of the natural-id.
	/// @param options The options to apply to the load operation
	/// @param session The session into which the entity is being loaded
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T load(Object naturalIdToLoad, Options options, SharedSessionContractImplementor session);

	/**
	 * Resolve the id from natural-id value
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object resolveNaturalIdToId(Object naturalIdValue, SharedSessionContractImplementor session);

	/**
	 * Resolve the natural-id value(s) from an id
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object resolveIdToNaturalId(Object id, SharedSessionContractImplementor session);
}
