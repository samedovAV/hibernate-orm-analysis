/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A discrete piece of work making use of a {@linkplain Connection JDBC connection}
 * and returning a result.
 *
 * @author Steve Ebersole
 *
 * @see Work
 * @see org.hibernate.SharedSessionContract#doReturningWork(ReturningWork)
 */
@FunctionalInterface
public interface ReturningWork<T> {
	/**
	 * Execute the discrete work encapsulated by this work instance using the supplied connection.
	 *
	 * @param connection The connection on which to perform the work.
	 *
	 * @return The work result
	 *
	 * @throws SQLException Thrown during execution of the underlying JDBC interaction.
	 * @throws org.hibernate.HibernateException Generally indicates a wrapped SQLException.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T execute(Connection connection) throws SQLException;
}
