/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.spi;

import org.hibernate.Incubating;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;

import java.util.function.BiConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Responsible for building a single {@link DomainResult}.
 * Given the following HQL for illustration,
 * <pre>
 *     select b from Book b join fetch b.authors
 * </pre>
 * we have a single result : `Book(b)`
 *
 * @see FetchBuilder
 *
 * @author Steve Ebersole
 */
@Incubating
public interface ResultBuilder extends GraphNodeBuilder {
	/**
	 * Build a result
	 *
	 * @param jdbcResultsMetadata The JDBC values and metadata
	 * @param resultPosition The position in the domain results for the result to be built
	 * @param domainResultCreationState Access to useful stuff
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResult<?> buildResult(
			JdbcValuesMetadata jdbcResultsMetadata,
			int resultPosition,
			DomainResultCreationState domainResultCreationState);

	/**
	 * The Java type of the value returned for a {@linkplain DomainResult result} built by this builder.
	 *
	 * @see DomainResult#getResultJavaType()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getJavaType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultBuilder cacheKeyInstance();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void visitFetchBuilders(BiConsumer<Fetchable, FetchBuilder> consumer) {
	}
}
