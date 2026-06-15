/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.spi;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.JavaServiceLoadable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for contributing services.
 *
 * @implSpec Implementations can be auto-discovered via Java's {@link java.util.ServiceLoader}
 * mechanism.
 *
 * @author Steve Ebersole
 */
@JavaServiceLoadable
public interface ServiceContributor {
	/**
	 * Contribute services to the indicated registry builder.
	 *
	 * @param serviceRegistryBuilder The builder to which services (or initiators) should be contributed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void contribute(@Nonnull StandardServiceRegistryBuilder serviceRegistryBuilder);
}
