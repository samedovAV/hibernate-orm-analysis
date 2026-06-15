/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SessionFactoryServiceInitiatorContext {
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getSessionFactory();
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryOptions getSessionFactoryOptions();
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistryImplementor getServiceRegistry();
}
