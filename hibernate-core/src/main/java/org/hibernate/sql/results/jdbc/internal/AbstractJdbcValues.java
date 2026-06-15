/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import org.hibernate.sql.results.jdbc.spi.JdbcValues;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractJdbcValues implements JdbcValues {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final boolean next(RowProcessingState rowProcessingState) {
		return processNext( rowProcessingState );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract boolean processNext(RowProcessingState rowProcessingState);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean previous(RowProcessingState rowProcessingState) {
		// NOTE: we do not even bother interacting with the query-cache put manager because
		//		 this method is implicitly related to scrolling and caching of scrolled results
		//		 is not supported
		return processPrevious( rowProcessingState );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract boolean processPrevious(RowProcessingState rowProcessingState);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean scroll(int numberOfRows, RowProcessingState rowProcessingState) {
		// NOTE: we do not even bother interacting with the query-cache put manager because
		//		 this method is implicitly related to scrolling and caching of scrolled results
		//		 is not supported
		return processScroll( numberOfRows, rowProcessingState );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract boolean processScroll(int numberOfRows, RowProcessingState rowProcessingState);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean position(int position, RowProcessingState rowProcessingState) {
		// NOTE: we do not even bother interacting with the query-cache put manager because
		//		 this method is implicitly related to scrolling and caching of scrolled results
		//		 is not supported
		return processPosition( position, rowProcessingState );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract boolean processPosition(int position, RowProcessingState rowProcessingState);
}
