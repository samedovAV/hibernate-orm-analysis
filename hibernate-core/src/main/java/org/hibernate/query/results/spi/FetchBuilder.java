/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.spi;

import org.hibernate.Incubating;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.Fetch;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;

import java.util.function.BiConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Responsible for building a single {@link Fetch} instance.
 * Given the following HQL for illustration,
 * <pre>
 *     select b from Book b join fetch b.authors
 * </pre>
 * we have a single fetch : `Book(b).authors`
 *
 * @see ResultBuilder
 *
 * @author Steve Ebersole
 */
@Incubating
public interface FetchBuilder extends GraphNodeBuilder {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Fetch buildFetch(
			FetchParent parent,
			NavigablePath fetchPath,
			JdbcValuesMetadata jdbcResultsMetadata,
			DomainResultCreationState domainResultCreationState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void visitFetchBuilders(BiConsumer<Fetchable, FetchBuilder> consumer) {
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchBuilder cacheKeyInstance();
}
