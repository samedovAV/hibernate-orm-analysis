/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.service.spi;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
@FunctionalInterface
public interface EventActionWithParameter<T, U, X> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyEventToListener(T eventListener, U action, X param);

}
