/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Lifecycle contract for services which wish to be notified when it is time to stop.
 *
 * @author Steve Ebersole
 */
public interface Stoppable {
	/**
	 * Stop phase notification
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void stop();
}
