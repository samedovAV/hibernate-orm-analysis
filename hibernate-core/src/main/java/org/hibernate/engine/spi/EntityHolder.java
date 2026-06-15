/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.sql.results.graph.entity.EntityInitializer;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingState;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Holder for an entry in the {@link PersistenceContext} for an {@link EntityKey}.
 *
 * @since 6.4
 */
@Incubating
public interface EntityHolder {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityKey getEntityKey();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getDescriptor();

	/**
	 * The entity object, or {@code null} if no entity object was registered yet.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEntity();
	/**
	 * The proxy object, or {@code null} if no proxy object was registered yet.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getProxy();
	/**
	 * The entity initializer that claims to initialize the entity for this holder.
	 * Will be {@code null} if entity is initialized already or the entity holder is not claimed yet.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityInitializer<?> getEntityInitializer();
	/**
	 * The {@link JdbcValuesSourceProcessingState} for the entity initializer that claims to initialize the entity for this holder.
	 * Will be {@code null} if entity is initialized already or the entity holder is not claimed yet.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesSourceProcessingState getJdbcValuesProcessingState();

	/**
	 * The proxy if there is one and otherwise the entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable Object getManagedObject() {
		final Object proxy = getProxy();
		return proxy == null ? getEntity() : proxy;
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityEntry getEntityEntry();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setEntityEntry(@Nullable EntityEntry entry);

	/**
	 * Marks the entity holder as reloaded to potentially trigger follow-on locking.
	 *
	 * @param processingState The processing state within which this entity is reloaded.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void markAsReloaded(JdbcValuesSourceProcessingState processingState);

	/**
	 * Whether the entity is already initialized
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInitialized();

	/**
	 * Whether the entity is already initialized or will be initialized through an initializer eventually.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isEventuallyInitialized();

	/**
	 * Whether the entity is detached.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDetached();


	/**
	 * For Hibernate Reactive
	 *
	 * Set the EntityInitializer to null
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void resetEntityInitialier();
}
