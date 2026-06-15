/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.scrollable;

import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.sql.results.internal.RowProcessingStateStandardImpl;
import org.hibernate.sql.results.jdbc.spi.JdbcValues;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingOptions;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingState;
import org.hibernate.sql.results.spi.RowReader;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base implementation of the ScrollableResults interface intended for sharing between
 * {@link ScrollableResultsImpl} and {@link FetchingScrollableResultsImpl}
 *
 * @author Steve Ebersole
 */
public abstract class AbstractScrollableResults<R> implements ScrollableResults<R> {
	private final JdbcValues jdbcValues;
	private final JdbcValuesSourceProcessingOptions processingOptions;
	private final JdbcValuesSourceProcessingState jdbcValuesSourceProcessingState;
	private final RowProcessingStateStandardImpl rowProcessingState;
	private final RowReader<R> rowReader;
	private final SharedSessionContractImplementor persistenceContext;

	private boolean closed;

	public AbstractScrollableResults(
			JdbcValues jdbcValues,
			JdbcValuesSourceProcessingOptions processingOptions,
			JdbcValuesSourceProcessingState jdbcValuesSourceProcessingState,
			RowProcessingStateStandardImpl rowProcessingState,
			RowReader<R> rowReader,
			SharedSessionContractImplementor persistenceContext) {
		this.jdbcValues = jdbcValues;
		this.processingOptions = processingOptions;
		this.jdbcValuesSourceProcessingState = jdbcValuesSourceProcessingState;
		this.rowProcessingState = rowProcessingState;
		this.rowReader = rowReader;
		this.persistenceContext = persistenceContext;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final R get() throws HibernateException {
		if ( closed ) {
			throw new IllegalStateException( "ScrollableResults is closed" );
		}
		return getCurrentRow();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract R getCurrentRow();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcValues getJdbcValues() {
		return jdbcValues;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcValuesSourceProcessingOptions getProcessingOptions() {
		return processingOptions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JdbcValuesSourceProcessingState getJdbcValuesSourceProcessingState() {
		return jdbcValuesSourceProcessingState;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected RowProcessingStateStandardImpl getRowProcessingState() {
		return rowProcessingState;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected RowReader<R> getRowReader() {
		return rowReader;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SharedSessionContractImplementor getPersistenceContext() {
		return persistenceContext;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected void afterScrollOperation() {
		getPersistenceContext().afterScrollOperation();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setFetchSize(int fetchSize) {
		getJdbcValues().setFetchSize( fetchSize );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final void close() {
		if ( !closed ) {
			rowReader.finishUp( rowProcessingState );
			jdbcValues.finishUp( persistenceContext );
			getPersistenceContext().getJdbcCoordinator().afterStatementExecution();
			closed = true;
		}
		// noop if already closed
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isClosed() {
		return closed;
	}
}
