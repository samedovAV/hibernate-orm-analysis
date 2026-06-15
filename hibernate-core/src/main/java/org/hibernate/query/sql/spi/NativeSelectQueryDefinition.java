/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sql.spi;

import java.util.List;
import java.util.Set;

import org.hibernate.query.results.spi.ResultSetMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access the values defining a native select query
 *
 * @author Steve Ebersole
 */
public interface NativeSelectQueryDefinition<R> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlString();

	/**
	 * @apiNote This returns query parameters in the order they were
	 * encountered - potentially including "duplicate references" to a single parameter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ParameterOccurrence> getQueryParameterOccurrences();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultSetMapping getResultSetMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getAffectedTableNames();

	// todo (6.0) : drop support for executing callables via NativeQuery
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCallable();

}
