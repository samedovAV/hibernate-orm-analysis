/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql.internal;

import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Something that can produce a DomainResult as part of a SQM interpretation
 *
 * @author Steve Ebersole
 */
public interface DomainResultProducer<T> {

	// this has to be designed as a bridge, but more geared toward the SQL

	/*
	 * select p.name, p2.name from Person p, Person p2
	 *
	 * SqmPathSource (SqmExpressible) (unmapped)
	 *
	 * DomainType
	 * SimpleDomainType
	 * ...
	 *
	 * MappingType
	 *
	 *
	 *
	 * ValueMapping (mapped)
	 *
	 *
	 * ModelPartContainer personMapping = ...;
	 * personMapping.getValueMapping( "name" );
	 */

	/**
	 * Produce the domain query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainResult<T> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState);

	/**
	 * Used when this producer is a selection in a sub-query.  The
	 * DomainResult is only needed for root query of a SELECT statement.
	 *
	 * This default impl assumes this producer is a true (Sql)Expression
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applySqlSelections(DomainResultCreationState creationState);
}
