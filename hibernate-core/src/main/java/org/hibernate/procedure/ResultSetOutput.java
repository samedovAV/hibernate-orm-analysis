/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Models a [java.sql.ResultSet] as an Output.
///
/// @author Steve Ebersole
public interface ResultSetOutput<T> extends Output {
	/// Consume the underlying [java.sql.ResultSet] and return the resulting List.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<T> getResultList();

	/// Consume the underlying [java.sql.ResultSet] with the expectation that there is just a single result.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getSingleResult();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ResultSetOutput<?> asResultSetOutput() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default UpdateCountOutput asUpdateCountOutput() {
		throw new IllegalOutputTypeException( "Cannot treat ResultSetOutput as UpdateCountOutput" );
	}
}
