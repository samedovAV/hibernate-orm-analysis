/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.spi;

import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.sql.results.graph.entity.EntityResult;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesMetadata;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ResultBuilder specialization for cases involving entity results.
 *
 * @see jakarta.persistence.EntityResult
 *
 * @author Steve Ebersole
 */
public interface ResultBuilderEntityValued extends ResultBuilder {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityResult<?> buildResult(
			JdbcValuesMetadata jdbcResultsMetadata,
			int resultPosition,
			DomainResultCreationState domainResultCreationState);
}
