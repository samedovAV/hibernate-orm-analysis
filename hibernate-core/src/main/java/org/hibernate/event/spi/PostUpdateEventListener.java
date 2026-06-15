/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Called after updating the datastore
 *
 * @author Gavin King
 */
public interface PostUpdateEventListener extends PostActionEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onPostUpdate(PostUpdateEvent event);
}
