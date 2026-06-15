/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.spi;

import java.sql.CallableStatement;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.ProcedureParameterMetadataImplementor;
import org.hibernate.sql.exec.spi.JdbcOperationQueryCall;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CallableStatementSupport {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcOperationQueryCall interpretCall(ProcedureCallImplementor procedureCall);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerParameters(
			String procedureName,
			JdbcOperationQueryCall procedureCall,
			CallableStatement statement,
			ProcedureParameterMetadataImplementor parameterMetadata,
			SharedSessionContractImplementor session);
}
