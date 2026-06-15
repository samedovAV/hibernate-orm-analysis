/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.spi;

import org.hibernate.SessionEventListener;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SessionEventListenerManager extends SessionEventListener {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addListener(SessionEventListener... listeners);
}
