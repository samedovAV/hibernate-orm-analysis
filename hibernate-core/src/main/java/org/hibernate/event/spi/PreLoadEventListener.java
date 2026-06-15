/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Called before injecting property values into a newly
 * loaded entity instance.
 *
 * @author Gavin King
 */
public interface PreLoadEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onPreLoad(PreLoadEvent event);
}
