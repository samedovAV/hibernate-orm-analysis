/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Callback to allow the built DelayedDropAction, if indicated, to be registered
 * back with the SessionFactory (or the thing that will manage its later execution).
 *
 * @author Steve Ebersole
 */
public interface DelayedDropRegistry {
	/**
	 * Register the built DelayedDropAction
	 *
	 * @param action The delayed schema drop memento
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerOnCloseAction(DelayedDropAction action);
}
