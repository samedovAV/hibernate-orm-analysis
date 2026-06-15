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
 * This interface provides a way to execute unrelated "work" objects using
 * polymorphism.
 *
 * Instances of this interface can accept a {@link WorkExecutor} visitor
 * for executing a discrete piece of work, and return an implementation-defined
 * result.
 *
 * @author Gail Badner
 */
public interface WorkExecutorVisitable<T> {
	/**
	 * Accepts a {@link WorkExecutor} visitor for executing a discrete
	 * piece of work, and returns an implementation-defined result..
	 *
	 * @param executor The visitor that executes the work.
	 * @param connection The connection on which to perform the work.
	 *
	 * @return an implementation-defined result
	 *
	 * @throws SQLException Thrown during execution of the underlying JDBC interaction.
	 * @throws org.hibernate.HibernateException Generally indicates a wrapped SQLException.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T accept(WorkExecutor<T> executor, Connection connection) throws SQLException;
}
