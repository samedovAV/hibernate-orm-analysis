/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import org.hibernate.LockMode;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.hibernate.sql.results.spi.RowReader;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * State pertaining to the processing of a single "row" of a JdbcValuesSource
 *
 * @author Steve Ebersole
 */
public interface RowProcessingState extends ExecutionContext {
	/**
	 * Access to the state related to the overall processing of the results.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesSourceProcessingState getJdbcValuesSourceProcessingState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode determineEffectiveLockMode(String alias);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean needsResolveState();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T extends InitializerData> T getInitializerData(int initializerId);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setInitializerData(int initializerId, InitializerData state);

	/**
	 * Retrieve the value corresponding to the given SqlSelection as part
	 * of the "current JDBC row".
	 *
	 * @see SqlSelection#getValuesArrayPosition()
	 * @see #getJdbcValue(int)
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Object getJdbcValue(SqlSelection sqlSelection) {
		return getJdbcValue( sqlSelection.getValuesArrayPosition() );
	}

	/**
	 * todo (6.0) : do we want this here?  Depends how we handle caching assembler / result memento
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RowReader<?> getRowReader();

	/**
	 * Retrieve the value corresponding to the given index as part
	 * of the "current JDBC row".
	 *
	 * We read all the ResultSet values for the given row one time
	 * and store them into an array internally based on the principle that multiple
	 * accesses to this array will be significantly faster than accessing them
	 * from the ResultSet potentially multiple times.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getJdbcValue(int position);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerNonExists(EntityFetch fetch);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isQueryCacheHit();

	/**
	 * Callback at the end of processing the current "row"
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishRowProcessing(boolean wasAdded);

	/**
	 * If this is a row processing state for aggregate components,
	 * this will return the underlying row processing state.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default RowProcessingState unwrap() {
		return this;
	}

}
