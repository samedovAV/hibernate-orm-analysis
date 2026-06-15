/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import java.util.Collection;

import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Manages the cached resolutions related to natural-id (to and from identifier)
 */
public interface NaturalIdResolutions {
	/**
	 * Marker reference used to indicate that a given natural-id is invalid
	 */
	Object INVALID_NATURAL_ID_REFERENCE = new Object();

	/**
	 * Caches a natural-id-to-identifier resolution. Handles both the local (transactional)
	 * and shared (second-level) caches.
	 *
	 * @return {@code true} if a new entry was actually added; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean cacheResolution(Object id, Object naturalId, EntityMappingType entityDescriptor);

	/**
	 * Removes a natural-id-to-identifier resolution.
	 * <p>
	 * Handles both the local (transactional) and shared (second-level) caches.
	 *
	 * @return The cached values, if any.  May be different from incoming values.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object removeResolution(Object id, Object naturalId, EntityMappingType entityDescriptor);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cacheResolutionFromLoad(Object id, Object naturalId, EntityMappingType entityDescriptor);

	/**
	 * Ensures that the necessary local cross-reference exists.  Specifically, this
	 * only effects the persistence-context cache, not the L2 cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void manageLocalResolution(
			Object id,
			Object naturalIdValue,
			EntityMappingType entityDescriptor,
			CachedNaturalIdValueSource source);

	/**
	 * Removes any local cross-reference, returning the previously cached value if one.
	 * <p>
	 * Again, this only effects the persistence-context cache, not the L2 cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object removeLocalResolution(Object id, Object naturalId, EntityMappingType entityDescriptor);

	/**
	 * Ensures that the necessary cross-reference exists in the L2 cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void manageSharedResolution(
			Object id,
			Object naturalId,
			Object previousNaturalId,
			EntityMappingType entityDescriptor,
			CachedNaturalIdValueSource source);

	/**
	 * Removes any cross-reference from the L2 cache
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeSharedResolution(Object id, Object naturalId, EntityMappingType entityDescriptor, boolean delayToAfterTransactionCompletion);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void removeSharedResolution(Object id, Object naturalId, EntityMappingType entityDescriptor) {
		removeSharedResolution( id, naturalId, entityDescriptor, false );
	}

	/**
	 * Find the cached natural-id for the given identifier
	 *
	 * @return The cross-referenced natural-id values or {@code null}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object findCachedNaturalIdById(Object id, EntityMappingType entityDescriptor);

	/**
	 * Find the cached identifier for the given natural-id
	 *
	 * @return The cross-referenced primary key, {@link #INVALID_NATURAL_ID_REFERENCE} or {@code null}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object findCachedIdByNaturalId(Object naturalId, EntityMappingType entityDescriptor);

	/**
	 * Find all the locally cached primary key cross-reference entries for the given entity.
	 *
	 * @return The primary keys
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<?> getCachedPkResolutions(EntityMappingType entityDescriptor);

	/**
	 * Part of the "load synchronization process".
	 * <p>
	 * Responsible for maintaining cross-reference entries when natural-id values were found
	 * to have changed.
	 * <p>
	 * Also responsible for tracking the old values as no longer valid until the next flush
	 * because otherwise going to the database would just re-pull the old values as valid.
	 * In this responsibility, {@link #cleanupFromSynchronizations} is the inverse process
	 * called after flush to clean up those entries.
	 *
	 * @see #cleanupFromSynchronizations
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleSynchronization(Object id, Object entity, EntityMappingType entityDescriptor);

	/**
	 * The clean up process of {@link #handleSynchronization}.  Responsible for cleaning up the tracking
	 * of old values as no longer valid.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cleanupFromSynchronizations();

	/**
	 * Called on {@link org.hibernate.Session#evict} to give a chance to clean up natural-id cross refs.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleEviction(Object id, Object object, EntityMappingType entityDescriptor);
}
