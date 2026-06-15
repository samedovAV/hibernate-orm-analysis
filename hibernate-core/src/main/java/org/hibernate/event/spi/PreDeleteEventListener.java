/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Called before deleting an item from the datastore
 *
 * @author Gavin King
 */
public interface PreDeleteEventListener {
	/**
	 * Return true if the operation should be vetoed
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean onPreDelete(PreDeleteEvent event);
}
