/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import jakarta.persistence.PessimisticLockScope;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Timeouts;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Event class for {@link org.hibernate.Session#lock}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.Session#lock
 */
public class LockEvent extends AbstractSessionEvent {
	public static final String ILLEGAL_SKIP_LOCKED = "Skip-locked is not valid option for #lock";

	private Object object;
	private final LockOptions lockOptions;
	private String entityName;

	public LockEvent(String entityName, Object object, LockOptions lockOptions, EventSource source) {
		super(source);

		if (object == null) {
			throw new IllegalArgumentException( "Entity may not be null" );
		}
		if (lockOptions == null) {
			throw new IllegalArgumentException( "LockOptions may not be null" );
		}
		if ( lockOptions.getLockMode() == LockMode.UPGRADE_SKIPLOCKED
			|| lockOptions.getTimeout().milliseconds() == Timeouts.SKIP_LOCKED_MILLI ) {
			throw new IllegalArgumentException( ILLEGAL_SKIP_LOCKED );
		}

		this.entityName = entityName;
		this.object = object;
		this.lockOptions = lockOptions;
	}

	public LockEvent(Object object, LockOptions lockOptions, EventSource source) {
		this( null, object, lockOptions, source );
	}

	/**
	 * @deprecated Use {@linkplain LockEvent#LockEvent(Object, LockOptions, EventSource)} instead.
	 */
	@Deprecated(since = "7", forRemoval = true)
	public LockEvent(Object object, LockMode lockMode, EventSource source) {
		this( object, lockMode.toLockOptions(), source );
	}

	/**
	 * @deprecated Use {@linkplain LockEvent#LockEvent(String, Object, LockOptions, EventSource)} instead.
	 */
	@Deprecated(since = "7", forRemoval = true)
	public LockEvent(String entityName, Object object, LockMode lockMode, EventSource source) {
		this( entityName, object, lockMode.toLockOptions(), source );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getObject() {
		return object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setObject(Object object) {
		this.object = object;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockOptions getLockOptions() {
		return lockOptions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityName(String entityName) {
		this.entityName = entityName;
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
		return lockOptions.getTimeOut();
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
