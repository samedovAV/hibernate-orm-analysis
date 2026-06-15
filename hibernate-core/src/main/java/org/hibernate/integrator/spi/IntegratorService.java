/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.integrator.spi;

import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface IntegratorService extends Service {
	/**
	 * Retrieve all integrators.
	 *
	 * @return All integrators.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Iterable<Integrator> getIntegrators();
}
