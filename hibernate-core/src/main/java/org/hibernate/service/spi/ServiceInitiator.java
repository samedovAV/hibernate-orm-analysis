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
 * Base contract for an initiator of a service.
 *
 * @author Steve Ebersole
 */
public interface ServiceInitiator<R extends Service> {
	/**
	 * Obtains the service role initiated by this initiator.  Should be unique within a registry
	 *
	 * @return The service role.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<R> getServiceInitiated();
}
