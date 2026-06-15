/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure;

import jakarta.persistence.sql.ResultSetMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Models a return that is an update count (count of rows affected)
///
/// @author Steve Ebersole
public interface UpdateCountOutput extends Output {
	/// Retrieve the associated update count
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getUpdateCount();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default UpdateCountOutput asUpdateCountOutput() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default ResultSetOutput<?> asResultSetOutput() {
		throw new  IllegalOutputTypeException( "Cannot treat UpdateCountOutput as ResultSetOutput" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> ResultSetOutput<X> asResultSetOutput(Class<X> resultType) {
		throw new  IllegalOutputTypeException( "Cannot treat UpdateCountOutput as ResultSetOutput" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> ResultSetOutput<X> asResultSetOutput(ResultSetMapping<X> resultSetMapping) {
		throw new  IllegalOutputTypeException( "Cannot treat UpdateCountOutput as ResultSetOutput" );
	}
}
