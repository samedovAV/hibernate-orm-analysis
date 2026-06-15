/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JdbcOperationQueryCall extends JdbcOperationQueryAnonBlock {
	/**
	 * If the call is a function, returns the function return descriptor
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcCallFunctionReturn getFunctionReturn();

	/**
	 * Get the list of any parameter registrations we need to register
	 * against the generated CallableStatement
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JdbcCallParameterRegistration> getParameterRegistrations();

	/**
	 * Extractors for reading back any OUT/INOUT parameters.
	 *
	 * @apiNote Note that REF_CURSOR parameters should be handled via
	 * {@link #getCallRefCursorExtractors()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JdbcCallParameterExtractor<?>> getParameterExtractors();

	/**
	 * Extractors for REF_CURSOR (ResultSet) parameters
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JdbcCallRefCursorExtractor> getCallRefCursorExtractors();
}
