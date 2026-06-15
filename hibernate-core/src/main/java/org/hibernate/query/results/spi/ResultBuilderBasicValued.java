/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.spi;

import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.basic.BasicResult;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ResultBuilder specialization for cases involving scalar results.
 *
 * @see jakarta.persistence.ColumnResult
 *
 * @author Steve Ebersole
 */
public interface ResultBuilderBasicValued extends ResultBuilder {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicResult<?> buildResult(
			JdbcValuesMetadata jdbcResultsMetadata,
			int resultPosition,
			DomainResultCreationState domainResultCreationState);
}
