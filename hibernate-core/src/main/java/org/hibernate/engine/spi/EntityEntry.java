/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.hibernate.Internal;
import org.hibernate.LockMode;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.internal.util.ImmutableBitSet;
import org.hibernate.persister.entity.EntityPersister;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Information about the current state of a managed entity instance with respect
 * to its persistent state.
 *
 * @implNote Hibernate instantiates very many of instances of this type,
 *           and so we need to take care of its impact on memory consumption.
 *
 * @author Gavin King
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 * @author Sanne Grinovero
 */
public interface EntityEntry {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode getLockMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLockMode(LockMode lockMode);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Status getStatus();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setStatus(Status status);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object[] getLoadedState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getLoadedValue(String propertyName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void overwriteLoadedStateCollectionValue(String propertyName, PersistentCollection<?> collection);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object[] getDeletedState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDeletedState(Object[] deletedState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isExistsInDatabase();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getVersion();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postInsert(Object version);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getPersister();

	/**
	 * Get the {@link EntityKey} for this entry.
	 *
	 * @return the {@link EntityKey}
	 * @throws IllegalStateException if {@link #getId()} is null
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityKey getEntityKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getRowId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postLoad(Object entity);

	/**
	 * Handle updating the internal state of the entry after actually performing
	 * the database update. Specifically, we update the snapshot information and
	 * escalate the lock mode.
	 *
	 * @param entity The entity instance
	 * @param updatedState The state calculated after the update (becomes the
	 * new {@link #getLoadedState() loaded state}.
	 * @param nextVersion The new version.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postUpdate(Object entity, Object[] updatedState, Object nextVersion);

	/**
	 * After actually deleting a row, record the fact that the instance no longer
	 * exists in the database.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postDelete();

	/**
	 * After actually inserting a row, record the fact that the instance exists
	 * in the database (needed for identity column key generation).
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postInsert(Object[] insertedState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isNullifiable(boolean earlyInsert, SharedSessionContractImplementor session);

	/**
	 * Returns {@code true} if the entity can possibly be dirty. This can only
	 * be the case if it is in a modifiable state (not read-only nor deleted)
	 * and it either has mutable properties or field-interception is not telling
	 * us that it is dirty.
	 *
	 * @param entity The entity to test
	 *
	 * @return {@code true} indicates that the entity could possibly be dirty
	 *         and that the dirty-check should happen;
	 *         {@code false} indicates there is no way the entity can be dirty
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean requiresDirtyCheck(Object entity);

	/**
	 * Can the entity be modified?
	 * <p>
	 * The entity is modifiable if all the following are true:
	 * <ul>
	 * <li>the entity class is mutable,
	 * <li>the entity is not read-only, and
	 * <li>if the current status is {@link Status#DELETED},
	 *     then the entity was not read-only when it was deleted.
	 * </ul>
	 *
	 * @return {@code true}, if the entity is modifiable;
	 *         {@code false}, otherwise,
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isModifiableEntity();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forceLocked(Object entity, Object nextVersion);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isReadOnly();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean setReadOnly(boolean readOnly, Object entity);

	/**
	 * Has a bit set for every attribute position that is potentially lazy.
	 * When {@code null}, no knowledge is available and every attribute must be assumed potentially lazy.
	 */
	@Internal
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 ImmutableBitSet getMaybeLazySet();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setMaybeLazySet(@Nullable ImmutableBitSet maybeLazySet);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String toString();

	/**
	 * Custom serialization routine used during serialization of a
	 * {@code Session}/{@code PersistenceContext} for increased
	 * performance.
	 *
	 * @param oos The stream to which we should write the serial data.
	 *
	 * @throws IOException If a stream error occurs
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void serialize(ObjectOutputStream oos) throws IOException;

	//the following methods are handling extraState contracts.
	//they are not shared by a common superclass to avoid alignment padding
	//we are trading off duplication for padding efficiency
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addExtraState(EntityEntryExtraState extraState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends EntityEntryExtraState> T getExtraState(Class<T> extraStateType);
}
