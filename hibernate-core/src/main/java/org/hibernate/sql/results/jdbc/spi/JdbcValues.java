/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides unified access to query results (JDBC values - see
 * {@link RowProcessingState#getJdbcValue} whether they come from
 * query cache or ResultSet.  Implementations also manage any cache puts
 * if required.
 *
 * @author Steve Ebersole
 */
public interface JdbcValues {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesMapping getValuesMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean usesFollowOnLocking();

	/**
	 * Advances the "cursor position" and returns a boolean indicating whether
	 * there is a row available to read via {@link #getCurrentRowValue(int)}.
	 *
	 * @return {@code true} if there are results
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean next(RowProcessingState rowProcessingState);

	/**
	 * Advances the "cursor position" in reverse and returns a boolean indicating whether
	 * there is a row available to read via {@link #getCurrentRowValue(int)}.
	 *
	 * @return {@code true} if there are results available
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean previous(RowProcessingState rowProcessingState);

	/**
	 * Advances the "cursor position" the indicated number of rows and returns a boolean
	 * indicating whether there is a row available to read via {@link #getCurrentRowValue(int)}.
	 *
	 * @param numberOfRows The number of rows to advance.  This can also be negative meaning to
	 * move in reverse
	 *
	 * @return {@code true} if there are results available
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean scroll(int numberOfRows, RowProcessingState rowProcessingState);

	/**
	 * Moves the "cursor position" to the specified position
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean position(int position, RowProcessingState rowProcessingState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getPosition();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isBeforeFirst(RowProcessingState rowProcessingState);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void beforeFirst(RowProcessingState rowProcessingState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isFirst(RowProcessingState rowProcessingState);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean first(RowProcessingState rowProcessingState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isAfterLast(RowProcessingState rowProcessingState);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterLast(RowProcessingState rowProcessingState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isLast(RowProcessingState rowProcessingState);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean last(RowProcessingState rowProcessingState);

	/**
	 * Get the JDBC value at the given index for the row currently positioned at within
	 * this source.
	 *
	 * @return The current row's JDBC values, or {@code null} if the position
	 * is beyond the end of the available results.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getCurrentRowValue(int valueIndex);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishRowProcessing(RowProcessingState rowProcessingState, boolean wasAdded);

	/**
	 * Give implementations a chance to finish processing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp(SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetchSize(int fetchSize);

	/**
	 * The estimate for the amount of results that can be expected for pre-sizing collections.
	 * May return zero or negative values if the count can not be reasonably estimated.
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getResultCountEstimate();
}
