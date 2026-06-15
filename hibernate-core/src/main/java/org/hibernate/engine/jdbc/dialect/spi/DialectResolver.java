/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.dialect.spi;

import org.hibernate.dialect.Dialect;
import org.hibernate.service.JavaServiceLoadable;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for determining the {@link Dialect} to use based on information about the database / driver.
 *
 * @author Tomoto Shimizu Washio
 * @author Steve Ebersole
 */
@JavaServiceLoadable
public interface DialectResolver extends Service {
	/**
	 * Determine the {@link Dialect} to use based on the given information.  Implementations are expected to return
	 * the {@link Dialect} instance to use, or {@code null} if they did not locate a match.
	 *
	 * @param info Access to the information about the database/driver needed to perform the resolution
	 *
	 * @return The dialect to use, or null.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Dialect resolveDialect(DialectResolutionInfo info);
}
