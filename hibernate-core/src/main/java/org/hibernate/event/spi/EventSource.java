/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacks;
import org.hibernate.engine.spi.TransactionCompletionCallbacksImplementor;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public interface EventSource extends SessionImplementor {

	/**
	 * Get the ActionQueue for this session
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	org.hibernate.action.queue.spi.ActionQueue getActionQueue();

	/**
	 * Instantiate an entity instance, using either an interceptor,
	 * or the given persister
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object instantiate(@Nonnull EntityPersister persister, @Nonnull Object id) throws HibernateException;

	/**
	 * Obtain the best estimate of the entity name of the given entity
	 * instance, which is not involved in an association, by also
	 * considering information held in the proxy, and whether the object
	 * is already associated with this session.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String bestGuessEntityName(@Nonnull Object object, @Nullable EntityEntry entry);

	/**
	 * Force an immediate flush
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forceFlush(EntityEntry e) throws HibernateException;
	/**
	 * Force an immediate flush
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forceFlush(EntityKey e) throws HibernateException;

	/**
	 * Cascade merge an entity instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void merge(String entityName, Object object, MergeContext copiedAlready) throws HibernateException;

	/**
	 * Cascade persist an entity instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void persist(String entityName, Object object, PersistContext createdAlready) throws HibernateException;

	/**
	 * Cascade persist an entity instance during the flush process
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void persistOnFlush(String entityName, Object object, PersistContext copiedAlready);

	/**
	 * Cascade refresh an entity instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void refresh(String entityName, Object object, RefreshContext refreshedAlready) throws HibernateException;

	/**
	 * Cascade delete an entity instance
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void delete(String entityName, Object child, boolean isCascadeDeleteEnabled, DeleteContext transientEntities);

	/**
	 * A specialized type of deletion for orphan removal that must occur prior to queued inserts and updates.
	 */
	// TODO: The removeOrphan concept is a temporary "hack" for HHH-6484.
	//       This should be removed once action/task ordering is improved.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void removeOrphanBeforeUpdates(String entityName, Object child);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default TransactionCompletionCallbacks getTransactionCompletionCallbacks() {
		return getActionQueue();
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default TransactionCompletionCallbacksImplementor getTransactionCompletionCallbacksImplementor() {
		return getActionQueue().getTransactionCompletionCallbacks();
	}
}
