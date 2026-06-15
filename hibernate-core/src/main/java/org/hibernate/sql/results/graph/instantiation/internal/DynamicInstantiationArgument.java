/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.instantiation.internal;

import org.hibernate.query.sqm.sql.BaseSqmToSqlAstConverter;
import org.hibernate.query.sqm.sql.internal.DomainResultProducer;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DynamicInstantiationArgument<T> {
	private final DomainResultProducer<T> argumentResultProducer;
	private final String alias;

	public DynamicInstantiationArgument(DomainResultProducer<T> argumentResultProducer, String alias) {
		this.argumentResultProducer = argumentResultProducer;
		this.alias = alias;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAlias() {
		return alias;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ArgumentDomainResult<T> buildArgumentDomainResult(DomainResultCreationState creationState) {
		final var sqlExpressionResolver =
				creationState.getSqlAstCreationState().getCurrentProcessingState()
						.getSqlExpressionResolver();
		if ( sqlExpressionResolver instanceof BaseSqmToSqlAstConverter.SqmAliasedNodeCollector ) {
			if ( !( argumentResultProducer instanceof DynamicInstantiation<?> ) ) {
				( (BaseSqmToSqlAstConverter.SqmAliasedNodeCollector) sqlExpressionResolver ).next();
			}
		}
		return new ArgumentDomainResult<>( argumentResultProducer.createDomainResult( alias, creationState ) );
	}
}
