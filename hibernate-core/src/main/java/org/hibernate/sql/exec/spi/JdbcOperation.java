/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A JDBC operation to perform.  This always equates to
 * some form of JDBC {@link java.sql.PreparedStatement} or
 * {@link java.sql.CallableStatement} execution.
 *
 * @author Steve Ebersole
 */
public interface JdbcOperation {
	/**
	 * The SQL command we will be executing through JDBC.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlString();

	/**
	 * The names of tables referred to by this operation.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getAffectedTableNames();

	/**
	 * The list of parameter binders for the generated PreparedStatement.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JdbcParameterBinder> getParameterBinders();
}
