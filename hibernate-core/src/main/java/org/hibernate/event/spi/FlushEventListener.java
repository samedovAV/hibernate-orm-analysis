/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of session flush events.
 *
 * @author Steve Ebersole
 */
public interface FlushEventListener {
	/**
	 * Handle the given flush event.
	 *
	 * @param event The flush event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onFlush(FlushEvent event) throws HibernateException;
}
