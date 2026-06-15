/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.connections.internal;

import java.sql.Connection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for creating JDBC {@linkplain Connection connections} on demand.
 *
 * @author Steve Ebersole
 */
public interface ConnectionCreator {
	/**
	 * Obtain the URL to which this creator connects.  Intended just for informational (logging) purposes.
	 *
	 * @return The connection URL.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUrl();

	/**
	 * Create a {@link Connection}
	 *
	 * @return The newly-created {@link Connection}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection createConnection();
}
