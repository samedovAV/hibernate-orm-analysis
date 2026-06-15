/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;

import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension to MutationOperation for cases where the operation wants to
 * handle execution itself.
 *
 * @author Steve Ebersole
 */
public interface SelfExecutingUpdateOperation extends MutationOperation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void performMutation(
			JdbcValueBindings jdbcValueBindings,
			ValuesAnalysis valuesAnalysis,
			SharedSessionContractImplementor session);

}
