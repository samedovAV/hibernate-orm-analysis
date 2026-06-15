/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.monitor.spi;

import org.hibernate.Incubating;
import org.hibernate.LockMode;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.access.CachedDomainDataAccess;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.event.spi.AutoFlushEvent;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.service.JavaServiceLoadable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract implemented by services which collect, report, or monitor
 * {@linkplain DiagnosticEvent diagnostic events} involving interactions
 * between the {@linkplain org.hibernate.Session session} and the database
 * or second-level cache.
 * <p>
 * For example, this interface is implemented by Hibernate JFR to report
 * events to Java Flight Recorder.
 * <p>
 * Note that event reporting is different to aggregate <em>metrics</em>,
 * which Hibernate exposes via the {@link org.hibernate.stat.Statistics}
 * interface.
 *
 * @apiNote This an incubating API, subject to change.
 *
 * @since 7.0
 */
@JavaServiceLoadable
@Incubating
public interface EventMonitor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginSessionOpenEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeSessionOpenEvent(
			DiagnosticEvent sessionOpenEvent,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginSessionClosedEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeSessionClosedEvent(
			DiagnosticEvent sessionClosedEvent,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginJdbcConnectionAcquisitionEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeJdbcConnectionAcquisitionEvent(
			DiagnosticEvent jdbcConnectionAcquisitionEvent,
			SharedSessionContractImplementor session,
			Object tenantId);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginJdbcConnectionReleaseEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeJdbcConnectionReleaseEvent(
			DiagnosticEvent jdbcConnectionReleaseEvent,
			SharedSessionContractImplementor session,
			Object tenantId);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginJdbcPreparedStatementCreationEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeJdbcPreparedStatementCreationEvent(
			DiagnosticEvent jdbcPreparedStatementCreation,
			String preparedStatementSql);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginJdbcPreparedStatementExecutionEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeJdbcPreparedStatementExecutionEvent(
			DiagnosticEvent jdbcPreparedStatementExecutionEvent,
			String preparedStatementSql);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginJdbcBatchExecutionEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeJdbcBatchExecutionEvent(
			DiagnosticEvent jdbcBatchExecutionEvent,
			String statementSql);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginCachePutEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			Region region,
			boolean cacheContentChanged,
			CacheActionDescription description);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			EntityPersister persister,
			boolean cacheContentChanged,
			CacheActionDescription description);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			EntityPersister persister,
			boolean cacheContentChanged,
			boolean isNatualId,
			CacheActionDescription description);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			CollectionPersister persister,
			boolean cacheContentChanged,
			CacheActionDescription description);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginCacheGetEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			boolean hit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			EntityPersister persister,
			boolean isNaturalKey,
			boolean hit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			CollectionPersister persister,
			boolean hit);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginFlushEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeFlushEvent(
			DiagnosticEvent flushEvent,
			org.hibernate.event.spi.FlushEvent event);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeFlushEvent(
			DiagnosticEvent flushEvent,
			org.hibernate.event.spi.FlushEvent event,
			boolean autoFlush);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginPartialFlushEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completePartialFlushEvent(
			DiagnosticEvent flushEvent,
			AutoFlushEvent event);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginDirtyCalculationEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeDirtyCalculationEvent(
			DiagnosticEvent dirtyCalculationEvent,
			SharedSessionContractImplementor session,
			EntityPersister persister,
			EntityEntry entry,
			int[] dirtyProperties);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginPrePartialFlush();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completePrePartialFlush(
			DiagnosticEvent prePartialFlush,
			SharedSessionContractImplementor session
	);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginEntityInsertEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeEntityInsertEvent(DiagnosticEvent event, Object id, String entityName, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginEntityUpdateEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeEntityUpdateEvent(DiagnosticEvent event, Object id, String entityName, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginEntityUpsertEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeEntityUpsertEvent(DiagnosticEvent event, Object id, String entityName, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginEntityDeleteEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeEntityDeleteEvent(DiagnosticEvent event, Object id, String entityName, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginEntityLockEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeEntityLockEvent(DiagnosticEvent event, Object id, String entityName, LockMode lockMode, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginCollectionRecreateEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCollectionRecreateEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginCollectionUpdateEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCollectionUpdateEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiagnosticEvent beginCollectionRemoveEvent();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void completeCollectionRemoveEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session);

	enum CacheActionDescription {
		ENTITY_INSERT( "Entity Insert" ),
		ENTITY_AFTER_INSERT( "Entity After Insert" ),
		ENTITY_UPDATE( "Entity Update" ),
		ENTITY_LOAD( "Entity Load" ),
		ENTITY_AFTER_UPDATE( "Entity After Update" ),
		TIMESTAMP_PRE_INVALIDATE( "Timestamp Pre Invalidate" ),
		TIMESTAMP_INVALIDATE( "Timestamp Invalidate" ),
		COLLECTION_INSERT( "Collection Insert" ),
		QUERY_RESULT( "Query Result" );


		private final String text;

		CacheActionDescription(String text) {
			this.text = text;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String toString() {
			return text;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getText() {
			return text;
		}
	}
}
