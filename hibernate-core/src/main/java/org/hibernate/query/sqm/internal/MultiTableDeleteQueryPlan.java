/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.spi.DomainQueryExecutionContext;
import org.hibernate.query.sqm.mutation.spi.MultiTableHandlerBuildResult;
import org.hibernate.query.sqm.mutation.spi.SqmMultiTableMutationStrategy;
import org.hibernate.query.sqm.tree.delete.SqmDeleteStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MultiTableDeleteQueryPlan extends AbstractMultiTableMutationQueryPlan<SqmDeleteStatement<?>, SqmMultiTableMutationStrategy> {

	public MultiTableDeleteQueryPlan(
			SqmDeleteStatement<?> sqmDelete,
			DomainParameterXref domainParameterXref,
			SqmMultiTableMutationStrategy deleteStrategy) {
		super( sqmDelete, domainParameterXref, deleteStrategy );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected MultiTableHandlerBuildResult buildHandler(
			SqmDeleteStatement<?> statement,
			DomainParameterXref domainParameterXref,
			SqmMultiTableMutationStrategy strategy,
			DomainQueryExecutionContext context) {
		return strategy.buildHandler( statement, domainParameterXref, context );
	}
}
