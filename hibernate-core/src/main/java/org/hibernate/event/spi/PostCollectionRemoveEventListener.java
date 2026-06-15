/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Called after removing a collection
 *
 * @author Gail Badner
 */
public interface PostCollectionRemoveEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onPostRemoveCollection(PostCollectionRemoveEvent event);
}
