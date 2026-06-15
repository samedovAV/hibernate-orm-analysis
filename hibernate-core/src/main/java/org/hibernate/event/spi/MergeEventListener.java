/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the contract for handling of merge events generated from a session.
 *
 * @author Gavin King
 */
public interface MergeEventListener {

	/**
	 * Handle the given merge event.
	 *
	 * @param event The merge event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onMerge(MergeEvent event) throws HibernateException;

	/**
	 * Handle the given merge event.
	 *
	 * @param event The merge event to be handled.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onMerge(MergeEvent event, MergeContext copiedAlready) throws HibernateException;

}
