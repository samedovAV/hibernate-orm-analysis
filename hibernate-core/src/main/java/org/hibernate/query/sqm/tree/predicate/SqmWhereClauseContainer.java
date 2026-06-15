/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.predicate;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Unified contract for things that can contain a SqmWhereClause.
 *
 * @author Steve Ebersole
 */
public interface SqmWhereClauseContainer {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmWhereClause getWhereClause();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyPredicate(SqmPredicate accept);
}
