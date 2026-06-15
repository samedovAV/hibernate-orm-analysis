/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import org.hibernate.engine.jdbc.batch.spi.BatchKey;
import org.hibernate.engine.jdbc.batch.spi.GroupedBatch;
import org.hibernate.engine.jdbc.batch.spi.StaleStateMapper;
import org.hibernate.engine.jdbc.mutation.TableInclusionChecker;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.hibernate.sql.model.ValuesAnalysis;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MutationExecutorSingleBatched extends AbstractSingleMutationExecutor {
	private final int batchSize;
	private final SharedSessionContractImplementor session;

	private final BatchKey batchKey;

	public MutationExecutorSingleBatched(
			PreparableMutationOperation mutationOperation,
			BatchKey batchKey,
			int batchSize,
			SharedSessionContractImplementor session) {
		super( mutationOperation, session );

		this.batchSize = batchSize;
		this.session = session;

		this.batchKey = batchKey;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected PreparedStatementGroupSingleTable getStatementGroup() {
		return (PreparedStatementGroupSingleTable) resolveBatch().getStatementGroup();
	}

	private GroupedBatch batch;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private GroupedBatch resolveBatch() {
		if ( batch == null ) {
			batch = session.getJdbcCoordinator().getGroupedBatch(
					batchKey,
					batchSize,
					getMutationOperation()
			);
			assert batch != null;
		}

		return batch;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void performBatchedOperations(
			ValuesAnalysis valuesAnalysis,
			TableInclusionChecker inclusionChecker,
			StaleStateMapper staleStateMapper) {
		resolveBatch().addToBatch( getJdbcValueBindings(), inclusionChecker, staleStateMapper );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
		// nothing to do
	}
}
