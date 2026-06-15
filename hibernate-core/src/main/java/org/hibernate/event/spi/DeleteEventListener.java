/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;


import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of deletion events generated from a session.
 *
 * @author Steve Ebersole
 */
public interface DeleteEventListener {

	/**
	 * Handle the given delete event.
	 *
	 * @param event The delete event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onDelete(DeleteEvent event) throws HibernateException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onDelete(DeleteEvent event, DeleteContext transientEntities) throws HibernateException;
}
