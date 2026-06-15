/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.spi;

import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.FetchParent;
import org.hibernate.sql.results.graph.basic.BasicFetch;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * FetchBuilder specialization for basic mappings
 *
 * @author Steve Ebersole
 */
public interface FetchBuilderBasicValued extends FetchBuilder {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicFetch<?> buildFetch(
			FetchParent parent,
			NavigablePath fetchPath,
			JdbcValuesMetadata jdbcResultsMetadata,
			DomainResultCreationState domainResultCreationState);
}
