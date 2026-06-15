/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.scrollable;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.sql.results.internal.RowProcessingStateStandardImpl;
import org.hibernate.sql.results.jdbc.spi.JdbcValues;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingOptions;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingState;
import org.hibernate.sql.results.spi.LoadContexts;
import org.hibernate.sql.results.spi.RowReader;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard {@link org.hibernate.ScrollableResults} implementation.
 *
 * @author Gavin King
 */
public class ScrollableResultsImpl<R> extends AbstractScrollableResults<R> {
	private R currentRow;

	public ScrollableResultsImpl(
			JdbcValues jdbcValues,
			JdbcValuesSourceProcessingOptions processingOptions,
			JdbcValuesSourceProcessingState jdbcValuesSourceProcessingState,
			RowProcessingStateStandardImpl rowProcessingState,
			RowReader<R> rowReader,
			SharedSessionContractImplementor persistenceContext) {
		super(
				jdbcValues,
				processingOptions,
				jdbcValuesSourceProcessingState,
				rowProcessingState,
				rowReader,
				persistenceContext
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected R getCurrentRow() {
		return currentRow;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean next() {
		final boolean result = getRowProcessingState().next();
		prepareCurrentRow( result );
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean previous() {
		final boolean result = getRowProcessingState().previous();
		prepareCurrentRow( result );
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean scroll(int i) {
		final boolean hasResult = getRowProcessingState().scroll( i );
		prepareCurrentRow( hasResult );
		return hasResult;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean position(int position) {
		final boolean hasResult = getRowProcessingState().position( position );
		prepareCurrentRow( hasResult );
		return hasResult;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean first() {
		final boolean hasResult = getRowProcessingState().first();
		prepareCurrentRow( hasResult );
		return hasResult;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean last() {
		final boolean hasResult = getRowProcessingState().last();
		prepareCurrentRow( hasResult );
		return hasResult;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterLast() {
		getRowProcessingState().afterLast();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void beforeFirst() {
		getRowProcessingState().beforeFirst();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFirst() {
		return getRowProcessingState().isFirst();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isLast() {
		return getRowProcessingState().isLast();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getRowNumber() {
		return getPosition() - 1;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPosition() {
		return getRowProcessingState().getPosition();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean setRowNumber(int rowNumber) {
		return position( rowNumber );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void prepareCurrentRow(boolean underlyingScrollSuccessful) {
		if ( underlyingScrollSuccessful ) {
			final PersistenceContext persistenceContext = getPersistenceContext().getPersistenceContext();
			final LoadContexts loadContexts = persistenceContext.getLoadContexts();
			loadContexts.register( getJdbcValuesSourceProcessingState() );
			persistenceContext.beforeLoad();
			try {
				try {
					currentRow = getRowReader().readRow( getRowProcessingState() );
					getRowProcessingState().finishRowProcessing( true );
					getJdbcValuesSourceProcessingState().finishUp();
				}
				finally {
					persistenceContext.afterLoad();
				}
				persistenceContext.initializeNonLazyCollections();
			}
			finally {
				loadContexts.deregister( getJdbcValuesSourceProcessingState() );
			}
			afterScrollOperation();
		}
		else {
			currentRow = null;
		}
	}

}
