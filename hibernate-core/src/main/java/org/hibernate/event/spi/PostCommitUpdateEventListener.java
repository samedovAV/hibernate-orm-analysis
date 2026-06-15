/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Called after an entity update is committed to the datastore.
 *
 * @author Shawn Clowater
 */
public interface PostCommitUpdateEventListener extends PostUpdateEventListener {
	/**
	 * Called when a commit fails and an entity was scheduled for update
	 *
	 * @param event the update event to be handled
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onPostUpdateCommitFailed(PostUpdateEvent event);
}
