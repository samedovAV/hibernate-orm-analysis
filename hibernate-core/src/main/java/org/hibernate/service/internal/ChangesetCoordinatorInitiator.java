/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.service.internal;

import jakarta.annotation.Nonnull;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.temporal.internal.ChangesetCoordinatorImpl;
import org.hibernate.temporal.spi.ChangesetCoordinator;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A service that acts as a source of changeset identifiers.
 *
 * @author Gavin King
 */
public class ChangesetCoordinatorInitiator implements StandardServiceInitiator<ChangesetCoordinator> {
	public static final ChangesetCoordinatorInitiator INSTANCE = new ChangesetCoordinatorInitiator();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<ChangesetCoordinator> getServiceInitiated() {
		return ChangesetCoordinator.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ChangesetCoordinator initiateService(@Nonnull Map<String, Object> configurationValues, @Nonnull ServiceRegistryImplementor registry) {
		return new ChangesetCoordinatorImpl(registry);
	}
}
