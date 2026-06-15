/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.find;

import org.hibernate.Incubating;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Context for performing load operations from [stateful sessions][SessionImplementor].
///
/// @author Steve Ebersole
@Incubating
public interface StatefulLoadAccessContext extends LoadAccessContext {
	/**
	 * The session from which the load originates
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionImplementor getSession();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SessionImplementor getEntityHandler() {
		return getSession();
	}

	/**
	 * Callback to check whether the session is "active"
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void checkOpenOrWaitingForAutoClose();

	/**
	 * Callback to pulse the transaction coordinator
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void pulseTransactionCoordinator();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void delayedAfterCompletion();

	/**
	 * Efficiently fire a {@link LoadEvent} with the given type
	 * and return the resulting entity instance or proxy.
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object load(
			LoadEventListener.LoadType loadType,
			Object id, String entityName,
			LockOptions lockOptions, Boolean readOnly);
}
