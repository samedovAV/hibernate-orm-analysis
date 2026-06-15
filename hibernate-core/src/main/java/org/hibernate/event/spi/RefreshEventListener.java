/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;


import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of refresh events generated from a session.
 *
 * @author Steve Ebersole
 */
public interface RefreshEventListener {

	/**
	 * Handle the given refresh event.
	 *
	 * @param event The refresh event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onRefresh(RefreshEvent event) throws HibernateException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onRefresh(RefreshEvent event, RefreshContext refreshedAlready) throws HibernateException;

}
