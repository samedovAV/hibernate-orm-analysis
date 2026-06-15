/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of lock events generated from a session.
 *
 * @author Steve Ebersole
 */
public interface LockEventListener {

	/** Handle the given lock event.
	 *
	 * @param event The lock event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onLock(LockEvent event) throws HibernateException;
}
