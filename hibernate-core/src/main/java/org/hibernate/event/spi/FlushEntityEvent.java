/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.engine.spi.EntityEntry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class FlushEntityEvent extends AbstractSessionEvent {

	private Object entity;
	private Object[] propertyValues;
	private Object[] databaseSnapshot;
	private int[] dirtyProperties;
	private boolean hasDirtyCollection;
	private boolean dirtyCheckPossible;
	private boolean dirtyCheckHandledByInterceptor;
	private EntityEntry entityEntry;
	private boolean allowedToReuse;//allows this event instance to be reused for multiple events: special case to GC
	private int instanceGenerationId;//in support of event instance reuse: to double check no recursive/nested use is happening

	public FlushEntityEvent(EventSource source, Object entity, EntityEntry entry) {
		super(source);
		this.entity = entity;
		this.entityEntry = entry;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityEntry getEntityEntry() {
		return entityEntry;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getDatabaseSnapshot() {
		return databaseSnapshot;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDatabaseSnapshot(Object[] databaseSnapshot) {
		this.databaseSnapshot = databaseSnapshot;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDatabaseSnapshot() {
		return databaseSnapshot!=null;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDirtyCheckHandledByInterceptor() {
		return dirtyCheckHandledByInterceptor;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDirtyCheckHandledByInterceptor(boolean dirtyCheckHandledByInterceptor) {
		this.dirtyCheckHandledByInterceptor = dirtyCheckHandledByInterceptor;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isDirtyCheckPossible() {
		return dirtyCheckPossible;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDirtyCheckPossible(boolean dirtyCheckPossible) {
		this.dirtyCheckPossible = dirtyCheckPossible;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int[] getDirtyProperties() {
		return dirtyProperties;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDirtyProperties(int[] dirtyProperties) {
		this.dirtyProperties = dirtyProperties;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDirtyProperties() {
		return dirtyProperties != null && dirtyProperties.length != 0;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDirtyCollection() {
		return hasDirtyCollection;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setHasDirtyCollection(boolean hasDirtyCollection) {
		this.hasDirtyCollection = hasDirtyCollection;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object[] getPropertyValues() {
		return propertyValues;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setPropertyValues(Object[] propertyValues) {
		this.propertyValues = propertyValues;
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntity() {
		return entity;
	}

	/**
	 * This is a terrible anti-pattern, but particular circumstances call for being
	 * able to reuse the same event instance: this is otherwise allocated in hot loops
	 * and since each event is escaping the scope it's actually causing allocation issues.
	 * The flush event does not appear to be used recursively so this is currently safe to
	 * do, nevertheless we add an allowedToReuse flag to ensure only instances whose
	 * purpose has completed are being reused.
	 * N.B. two out of three parameters from the constructor are reset: the same EventSource is implied
	 * on reuse.
	 * @param entity same as constructor parameter
	 * @param entry same as constructor parameter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void resetAndReuseEventInstance(Object entity, EntityEntry entry) {
		this.entity = entity;
		this.entityEntry = entry;
		this.allowedToReuse = false;
		//and reset other fields to the default:
		this.propertyValues = null;
		this.databaseSnapshot = null;
		this.dirtyProperties = null;
		this.hasDirtyCollection = false;
		this.dirtyCheckPossible = false;
		this.dirtyCheckHandledByInterceptor = false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAllowedToReuse() {
		return this.allowedToReuse;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAllowedToReuse(final boolean allowedToReuse) {
		this.allowedToReuse = allowedToReuse;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getInstanceGenerationId() {
		return this.instanceGenerationId;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInstanceGenerationId(final int instanceGenerationId) {
		this.instanceGenerationId = instanceGenerationId;
	}
}
