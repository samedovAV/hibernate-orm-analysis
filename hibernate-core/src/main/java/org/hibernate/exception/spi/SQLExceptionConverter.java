/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.exception.spi;

import java.io.Serializable;
import java.sql.SQLException;

import org.hibernate.JDBCException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An object that interprets JDBC {@link SQLException}s and converts
 * them to subtypes of Hibernate {@link JDBCException}s.
 *
 * @author Steve Ebersole
 */
public interface SQLExceptionConverter extends Serializable {
	/**
	 * Convert the given {@link SQLException} to a subtype of
	 * {@link JDBCException}.
	 *
	 * @param sqlException The {@code SQLException} to be converted
	 * @param message An optional error message
	 * @param sql The SQL statement that resulted in the exception
	 *
	 * @return The resulting {@code JDBCException}.
	 *
	 * @see org.hibernate.exception.ConstraintViolationException
	 * @see org.hibernate.exception.JDBCConnectionException
	 * @see org.hibernate.exception.SQLGrammarException
	 * @see org.hibernate.exception.LockAcquisitionException
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JDBCException convert(SQLException sqlException, String message, String sql);
}
