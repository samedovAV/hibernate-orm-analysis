/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Marker interface for non-contextually created {@link java.sql.Clob} instances.
 *
 * @author Steve Ebersole
 */
public interface ClobImplementer {
	/**
	 * Gets access to the data underlying this CLOB.
	 *
	 * @return Access to the underlying data.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CharacterStream getUnderlyingStream();
}
