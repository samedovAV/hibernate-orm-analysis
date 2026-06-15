/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Listener for notification of {@link org.hibernate.Session#clear()}
 *
 * @author Steve Ebersole
 */
public interface ClearEventListener {
	/**
	 * Callback for {@link org.hibernate.Session#clear()} notification
	 *
	 * @param event The event representing the clear
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void onClear(ClearEvent event);
}
