/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.hql.spi;

import org.hibernate.query.sqm.tree.domain.SqmPath;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Contract for things that can be part of a path structure, including:
///
/// * package name
/// * class name
/// * field name
/// * enum name
/// * [org.hibernate.query.sqm.tree.domain.SqmSimplePath]
///
/// @author Steve Ebersole
public interface SemanticPathPart {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SemanticPathPart resolvePathPart(
			String name,
			boolean isTerminal,
			SqmCreationState creationState);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<?> resolveIndexedAccess(
			SqmExpression<?> selector,
			boolean isTerminal,
			SqmCreationState creationState);
}
