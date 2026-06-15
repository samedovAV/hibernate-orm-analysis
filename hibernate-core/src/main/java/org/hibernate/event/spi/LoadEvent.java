/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import jakarta.persistence.PessimisticLockScope;
import org.hibernate.Internal;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *  Defines an event class for the loading of an entity.
 *
 * @author Steve Ebersole
 */
public class LoadEvent extends AbstractSessionEvent {

	private Object entityId;
	private String entityClassName;
	private Object instanceToLoad;
	private LockOptions lockOptions;
	private boolean isAssociationFetch;
	private Object result;
	private Boolean readOnly;

	public LoadEvent(Object entityId, Object instanceToLoad, EventSource source, Boolean readOnly) {
		this( entityId, null, instanceToLoad, LockMode.NONE.toLockOptions(), false, source, readOnly );
	}

	public LoadEvent(Object entityId, String entityClassName, LockMode lockMode, EventSource source, Boolean readOnly) {
		this( entityId, entityClassName, null, lockMode, false, source, readOnly );
	}

	public LoadEvent(Object entityId, String entityClassName, LockOptions lockOptions, EventSource source, Boolean readOnly) {
		this( entityId, entityClassName, null, lockOptions, false, source, readOnly );
	}

	public LoadEvent(Object entityId, String entityClassName, boolean isAssociationFetch, EventSource source, Boolean readOnly) {
		this( entityId, entityClassName, null, LockOptions.NONE, isAssociationFetch, source, readOnly );
	}

	private LoadEvent(
			Object entityId,
			String entityClassName,
			Object instanceToLoad,
			LockMode lockMode,
			boolean isAssociationFetch,
			EventSource source,
			Boolean readOnly) {
		this(
				entityId,
				entityClassName,
				instanceToLoad,
				lockMode.toLockOptions(),
				isAssociationFetch,
				source,
				readOnly
		);
	}

	private LoadEvent(
			Object entityId,
			String entityClassName,
			Object instanceToLoad,
			LockOptions lockOptions,
			boolean isAssociationFetch,
			EventSource source,
			Boolean readOnly) {
		super( source );
		this.entityId = entityId;
		this.entityClassName = entityClassName;
		this.instanceToLoad = instanceToLoad;
		this.lockOptions = lockOptions;
		this.isAssociationFetch = isAssociationFetch;
		this.readOnly = readOnly;
		validate();
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void validate() {
		if ( entityId == null ) {
			throw new IllegalArgumentException( "Identifier may not be null" );
		}

		final var lockMode = lockOptions.getLockMode();
		if ( lockMode == LockMode.WRITE ) {
			throw new IllegalArgumentException( "Invalid lock mode: " + LockMode.WRITE );
		}
		else if ( lockMode == null ) {
			lockOptions.setLockMode( LockMode.NONE );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntityId() {
		return entityId;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityId(Object entityId) {
		this.entityId = entityId;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityClassName() {
		return entityClassName;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAssociationFetch() {
		return isAssociationFetch;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setAssociationFetch(boolean associationFetch) {
		isAssociationFetch = associationFetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getInstanceToLoad() {
		return instanceToLoad;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInstanceToLoad(Object instanceToLoad) {
		this.instanceToLoad = instanceToLoad;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLockOptions(LockOptions lockOptions) {
		this.lockOptions = lockOptions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getResult() {
		return result;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setResult(Object result) {
		this.result = result;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getReadOnly() {
		return readOnly;
	}

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * @deprecated Use {@linkplain #getLockOptions()} instead.
	 */
	@Deprecated(since = "7.1")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public LockMode getLockMode() {
		return lockOptions.getLockMode();
	}

	/**
	 * @deprecated Use {@linkplain #getLockOptions()} instead.
	 */
	@Deprecated(since = "7.1")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getLockTimeout() {
		return lockOptions.getTimeout().milliseconds();
	}

	/**
	 * @deprecated Use {@linkplain #getLockOptions()} instead.
	 */
	@Deprecated(since = "7.1")
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean getLockScope() {
		return lockOptions.getLockScope() != PessimisticLockScope.NORMAL;
	}
}
