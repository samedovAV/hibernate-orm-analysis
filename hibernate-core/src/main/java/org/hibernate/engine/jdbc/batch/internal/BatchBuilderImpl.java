/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.batch.internal;

import java.util.function.Supplier;

import org.hibernate.Internal;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
import org.hibernate.engine.jdbc.batch.spi.BatchKey;
import org.hibernate.engine.jdbc.batch.spi.GroupedBatch;
import org.hibernate.engine.jdbc.batch.spi.SingleStatementBatch;
import org.hibernate.engine.jdbc.mutation.group.PreparedStatementGroup;
import org.hibernate.engine.jdbc.mutation.internal.PreparedStatementGroupSingleTable;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.model.PreparableMutationOperation;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.jdbc.JdbcInsertMutation;

import static java.util.Collections.emptyList;
import static org.hibernate.engine.jdbc.batch.JdbcBatchLogging.BATCH_MESSAGE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A builder for {@link Batch} instances.
 *
 * @author Steve Ebersole
 */
public class BatchBuilderImpl implements BatchBuilder {
	private final int globalBatchSize;

	/**
	 * Constructs a BatchBuilderImpl
	 *
	 * @param globalBatchSize The batch size to use.  Can be overridden
	 * on {@link #buildGroupedBatch}
	 */
	public BatchBuilderImpl(int globalBatchSize) {
		if ( globalBatchSize > 1 ) {
			BATCH_MESSAGE_LOGGER.batchingEnabled( globalBatchSize );
		}
		BATCH_MESSAGE_LOGGER.usingStandardBatchBuilder();
		this.globalBatchSize = globalBatchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcBatchSize() {
		return globalBatchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private int batchSize(Integer explicitBatchSize) {
		return explicitBatchSize == null
				? globalBatchSize
				: explicitBatchSize;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GroupedBatch buildGroupedBatch(
			BatchKey key,
			Integer explicitBatchSize,
			Supplier<PreparedStatementGroup> statementGroupSupplier,
			JdbcCoordinator jdbcCoordinator) {
		final int batchSize = batchSize( explicitBatchSize );
		assert batchSize > 1;
		return new BatchImpl( key, statementGroupSupplier.get(), batchSize, jdbcCoordinator );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SingleStatementBatch buildSingleStatementBatch(
			BatchKey key,
			Integer explicitBatchSize,
			PreparableMutationOperation mutationOperation,
			JdbcCoordinator jdbcCoordinator) {
		final int batchSize = batchSize( explicitBatchSize );
		assert batchSize > 1;
		return new SingleStatementBatchImpl( key, mutationOperation, batchSize, jdbcCoordinator );
	}

	/**
	 * Intended for use from tests
	 */
	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BatchImpl buildBatch(BatchKey batchKey, Integer sizeOverride, String table, SessionImplementor session, String sql) {
		return new BatchImpl(
				batchKey,
				new PreparedStatementGroupSingleTable(
						new JdbcInsertMutation(
								new TableMapping() {
									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public String getTableName() {
										return table;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public int relativePosition() {
										return 0;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public KeyDetails getKeyDetails() {
										return null;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public boolean isOptional() {
										return false;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public boolean isInverse() {
										return false;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public boolean isIdentifierTable() {
										return true;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public MutationDetails getInsertDetails() {
										return null;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public MutationDetails getUpdateDetails() {
										return null;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public boolean isCascadeDeleteEnabled() {
										return false;
									}

									@Override
									@Prove(complexity = Complexity.O_1, n = "", count = {})
									public MutationDetails getDeleteDetails() {
										return null;
									}
								},
								null,
								sql,
								false,
								Expectation.None.INSTANCE,
								emptyList()
						),
						session
				),
				batchSize( sizeOverride ),
				session.getJdbcCoordinator()
		);
	}
}
