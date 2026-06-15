/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SessionFactoryServiceRegistryBuilder {
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryServiceRegistryBuilder addInitiator(@Nonnull SessionFactoryServiceInitiator<?> initiator);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<R extends Service> SessionFactoryServiceRegistryBuilder addService(@Nonnull Class<R> serviceRole, R service);
}
