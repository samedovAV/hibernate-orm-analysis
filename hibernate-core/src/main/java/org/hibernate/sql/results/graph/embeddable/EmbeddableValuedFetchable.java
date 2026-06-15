/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.embeddable;

import org.hibernate.sql.ast.SqlAstJoinType;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface EmbeddableValuedFetchable extends EmbeddableValuedModelPart, Fetchable {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqlAstJoinType getDefaultSqlAstJoinType(TableGroup parentTableGroup) {
		return SqlAstJoinType.LEFT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSimpleJoinPredicate(Predicate predicate) {
		return predicate == null;
	}
}
