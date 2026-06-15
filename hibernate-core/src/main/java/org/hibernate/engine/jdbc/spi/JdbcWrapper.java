/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * Generic contract for wrapped JDBC objects.
 *
 * @param <T> One of either {@link java.sql.Connection}, {@link java.sql.Statement} or {@link java.sql.ResultSet}
 *
 * @author Steve Ebersole
 */
public interface JdbcWrapper<T> {
	/**
	 * Retrieve the wrapped JDBC object.
	 *
	 * @return The wrapped object
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T getWrappedObject();
}
