/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface NonSelectQueryPlan extends QueryPlan {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int executeUpdate(DomainQueryExecutionContext executionContext);
}
