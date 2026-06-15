/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.jpa.spi;

import jakarta.persistence.EntityListenerRegistration;
import org.hibernate.jpa.event.spi.CallbackType;

import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Jakarta Persistence style callbacks for a particular entity.
///
/// @param <E> The entity type
///
/// @author Steve Ebersole
public interface EntityCallbacks<E> {
	/// Whether there are any callbacks registered for the entity of this type.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasRegisteredCallbacks(CallbackType callbackType);

	/// Handle [jakarta.persistence.PrePersist] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preCreate(S entity);

	/// Handle [jakarta.persistence.PostPersist] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postCreate(S entity);

	/// Handle [jakarta.persistence.PreMerge] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preMerge(S entity);

	/// Handle [jakarta.persistence.PreInsert] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preInsert(S entity);

	/// Handle [jakarta.persistence.PostInsert] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postInsert(S entity);

	/// Handle [jakarta.persistence.PreUpdate] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preUpdate(S entity);

	/// Handle [jakarta.persistence.PostUpdate] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postUpdate(S entity);

	/// Handle [jakarta.persistence.PreUpsert] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preUpsert(S entity);

	/// Handle [jakarta.persistence.PostUpsert] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postUpsert(S entity);

	/// Handle [jakarta.persistence.PreRemove] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preRemove(S entity);

	/// Handle [jakarta.persistence.PostRemove] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postRemove(S entity);

	/// Handle [jakarta.persistence.PreDelete] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean preDelete(S entity);

	/// Handle [jakarta.persistence.PostDelete] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postDelete(S entity);

	/// Handle [jakarta.persistence.PostLoad] callbacks.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends E> boolean postLoad(S entity);

	/// @see jakarta.persistence.EntityManagerFactory#addListener
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityListenerRegistration addListener(CallbackType type, Consumer<? super E> listener);

	/// @see jakarta.persistence.EntityManagerFactory#addListener
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityListenerRegistration addListener(CallbackType type, Callback<? super E> callback);
}
