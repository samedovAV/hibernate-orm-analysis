/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.hql.internal;

import org.hibernate.query.hql.spi.SemanticPathPart;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized "intermediate" SemanticPathPart for processing domain model paths/
 *
 * @author Steve Ebersole
 */
public class DomainPathPart implements SemanticPathPart {
	private SqmPath<?> currentPath;

	public DomainPathPart(SqmPath<?> basePath) {
		this.currentPath = basePath;
		assert currentPath != null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<?> getSqmExpression() {
		return currentPath;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SemanticPathPart resolvePathPart(
			String name,
			boolean isTerminal,
			SqmCreationState creationState) {
//		HqlLogging.QUERY_LOGGER.tracef(
//				"Resolving DomainPathPart(%s) sub-part : %s",
//				currentPath,
//				name
//		);
		currentPath = currentPath.resolvePathPart( name, isTerminal, creationState );
		if ( isTerminal ) {
			return currentPath;
		}
		else {
			return this;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqmPath<?> resolveIndexedAccess(
			SqmExpression<?> selector,
			boolean isTerminal,
			SqmCreationState creationState) {
		return currentPath.resolveIndexedAccess( selector, isTerminal, creationState );
	}
}
