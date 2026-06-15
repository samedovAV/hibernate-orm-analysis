/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Occurs after an entity instance is fully loaded.
 *
 * @author Kabir Khan
 */
public interface PostLoadEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onPostLoad(PostLoadEvent event);
}
