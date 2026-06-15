/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.event.spi;

import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@linkplain Service service} which creates new instances of {@link EntityCopyObserver}.
 */
@FunctionalInterface
public interface EntityCopyObserverFactory extends Service {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityCopyObserver createEntityCopyObserver();
}
