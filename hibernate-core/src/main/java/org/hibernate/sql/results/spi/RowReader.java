/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.spi;

import java.util.List;

import org.hibernate.engine.spi.EntityKey;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.type.descriptor.java.JavaType;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Coordinates the process of reading a single result values row
 *
 * @author Steve Ebersole
 */
public interface RowReader<R> {
	/**
	 * The type actually returned from this reader's {@link #readRow} call,
	 * accounting for any transformers.
	 * <p>
	 * May be {@code null} to indicate that no transformation is applied.
	 * <p>
	 * Ultimately intended for use in comparing values that are being de-duplicated
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<R> getDomainResultResultJavaType();

	/**
	 * The individual JavaType for each DomainResult
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JavaType<?>> getResultJavaTypes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getInitializerCount();

	/**
	 * Called before reading the first row.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void startLoading(RowProcessingState processingState);

	/**
	 * The actual coordination of reading a row
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	R readRow(RowProcessingState processingState);

	/**
	 * Called at the end of processing all rows
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp(RowProcessingState processingState);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityKey resolveSingleResultEntityKey(RowProcessingState rowProcessingState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasCollectionInitializers();

}
