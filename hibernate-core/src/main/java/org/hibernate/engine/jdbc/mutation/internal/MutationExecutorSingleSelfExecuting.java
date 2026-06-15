/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.engine.jdbc.mutation.group.PreparedStatementDetails;
import org.hibernate.engine.jdbc.mutation.spi.JdbcValueDescriptorAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.sql.model.SelfExecutingUpdateOperation;
import org.hibernate.sql.model.ValuesAnalysis;
import org.hibernate.sql.model.jdbc.JdbcValueDescriptor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MutationExecutorSingleSelfExecuting extends AbstractMutationExecutor implements JdbcValueDescriptorAccess {
	private final SelfExecutingUpdateOperation operation;
	private final JdbcValueBindingsImpl valueBindings;

	public MutationExecutorSingleSelfExecuting(
			SelfExecutingUpdateOperation operation,
			SharedSessionContractImplementor session) {
		this.operation = operation;

		this.valueBindings = new JdbcValueBindingsImpl(
				operation.getMutationType(),
				operation.getMutationTarget(),
				this,
				session
		);

		prepareForNonBatchedWork( null, session );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValueDescriptor resolveValueDescriptor(String tableName, String columnName, ParameterUsage usage) {
		return operation.findValueDescriptor( columnName, usage );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValueBindings getJdbcValueBindings() {
		return valueBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PreparedStatementDetails getPreparedStatementDetails(String tableName) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void performSelfExecutingOperations(
			ValuesAnalysis valuesAnalysis,
			TableInclusionChecker inclusionChecker,
			SharedSessionContractImplementor session) {
		if ( inclusionChecker.include( operation.getTableDetails() ) ) {
			operation.performMutation( valueBindings, valuesAnalysis, session );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
		// todo (mutation) :implement
	}
}
