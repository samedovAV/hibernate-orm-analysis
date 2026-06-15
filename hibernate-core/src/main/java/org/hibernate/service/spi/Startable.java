/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Lifecycle contract for services which wish to be notified when it is time to start.
 *
 * @author Steve Ebersole
 */
public interface Startable {
	/**
	 * Start phase notification
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void start();
}
