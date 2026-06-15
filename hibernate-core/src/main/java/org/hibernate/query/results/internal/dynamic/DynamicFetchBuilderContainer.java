/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.dynamic;

import org.hibernate.query.results.spi.FetchBuilder;
import org.hibernate.sql.results.graph.Fetchable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface DynamicFetchBuilderContainer {
	/**
	 * Locate an explicit fetch definition for the named fetchable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchBuilder findFetchBuilder(Fetchable fetchable);

	/**
	 * Add a property mapped to a single column.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicFetchBuilderContainer addProperty(Fetchable fetchable, String columnAlias);

	/**
	 * Add a property mapped to multiple columns
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicFetchBuilderContainer addProperty(Fetchable fetchable, String... columnAliases);

	/**
	 * Add a property whose columns can later be defined using {@link DynamicFetchBuilder#addColumnAlias}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicFetchBuilder addProperty(Fetchable fetchable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addFetchBuilder(Fetchable fetchable, FetchBuilder fetchBuilder);
}
