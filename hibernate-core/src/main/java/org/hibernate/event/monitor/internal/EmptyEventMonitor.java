/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.monitor.internal;

import org.hibernate.LockMode;
import org.hibernate.cache.spi.Region;
import org.hibernate.cache.spi.access.CachedDomainDataAccess;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.event.spi.AutoFlushEvent;
import org.hibernate.event.monitor.spi.EventMonitor;
import org.hibernate.event.monitor.spi.DiagnosticEvent;
import org.hibernate.event.spi.FlushEvent;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An {@link EventMonitor} that ignores all events.
 */
public final class EmptyEventMonitor implements EventMonitor {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginSessionOpenEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeSessionOpenEvent(DiagnosticEvent sessionOpenEvent, SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginSessionClosedEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeSessionClosedEvent(
			DiagnosticEvent sessionClosedEvent,
			SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginJdbcConnectionAcquisitionEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeJdbcConnectionAcquisitionEvent(
			DiagnosticEvent jdbcConnectionAcquisitionEvent,
			SharedSessionContractImplementor session,
			Object tenantId) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginJdbcConnectionReleaseEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeJdbcConnectionReleaseEvent(
			DiagnosticEvent jdbcConnectionReleaseEvent,
			SharedSessionContractImplementor session,
			Object tenantId) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginJdbcPreparedStatementCreationEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeJdbcPreparedStatementCreationEvent(
			DiagnosticEvent jdbcPreparedStatementCreation,
			String preparedStatementSql) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginJdbcPreparedStatementExecutionEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeJdbcPreparedStatementExecutionEvent(
			DiagnosticEvent jdbcPreparedStatementExecutionEvent,
			String preparedStatementSql) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginJdbcBatchExecutionEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeJdbcBatchExecutionEvent(DiagnosticEvent jdbcBatchExecutionEvent, String statementSql) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginCachePutEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			Region region,
			boolean cacheContentChanged,
			CacheActionDescription description) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			EntityPersister persister,
			boolean cacheContentChanged,
			CacheActionDescription description) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			EntityPersister persister,
			boolean cacheContentChanged,
			boolean isNatualId,
			CacheActionDescription description) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCachePutEvent(
			DiagnosticEvent cachePutEvent,
			SharedSessionContractImplementor session,
			CachedDomainDataAccess cachedDomainDataAccess,
			CollectionPersister persister,
			boolean cacheContentChanged,
			CacheActionDescription description) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginCacheGetEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			boolean hit) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			EntityPersister persister,
			boolean isNaturalKey,
			boolean hit) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCacheGetEvent(
			DiagnosticEvent cacheGetEvent,
			SharedSessionContractImplementor session,
			Region region,
			CollectionPersister persister,
			boolean hit) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginFlushEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeFlushEvent(DiagnosticEvent flushEvent, FlushEvent event) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeFlushEvent(DiagnosticEvent flushEvent, FlushEvent event, boolean autoFlush) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginPartialFlushEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completePartialFlushEvent(DiagnosticEvent flushEvent, AutoFlushEvent event) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginDirtyCalculationEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeDirtyCalculationEvent(
			DiagnosticEvent dirtyCalculationEvent,
			SharedSessionContractImplementor session,
			EntityPersister persister,
			EntityEntry entry,
			int[] dirtyProperties) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginPrePartialFlush() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completePrePartialFlush(
			DiagnosticEvent prePartialFlush,
			SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginEntityInsertEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeEntityInsertEvent(
			DiagnosticEvent event,
			Object id, String entityName,
			boolean success,
			SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginEntityUpdateEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeEntityUpdateEvent(
			DiagnosticEvent event,
			Object id, String entityName,
			boolean success,
			SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginEntityUpsertEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeEntityUpsertEvent(DiagnosticEvent event, Object id, String entityName, boolean success, SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginEntityDeleteEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeEntityDeleteEvent(
			DiagnosticEvent event,
			Object id, String entityName,
			boolean success,
			SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginEntityLockEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeEntityLockEvent(DiagnosticEvent event, Object id, String entityName, LockMode lockMode, boolean success, SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginCollectionRecreateEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCollectionRecreateEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginCollectionUpdateEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCollectionUpdateEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session) {

	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DiagnosticEvent beginCollectionRemoveEvent() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void completeCollectionRemoveEvent(DiagnosticEvent event, Object id, String role, boolean success, SharedSessionContractImplementor session) {

	}
}
