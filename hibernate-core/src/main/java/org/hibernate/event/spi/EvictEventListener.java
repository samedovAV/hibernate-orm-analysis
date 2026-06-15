/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of evict events generated from a session.
 *
 * @author Steve Ebersole
 */
public interface EvictEventListener {

	/**
	 * Handle the given evict event.
	 *
	 * @param event The evict event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onEvict(EvictEvent event) throws HibernateException;
}
