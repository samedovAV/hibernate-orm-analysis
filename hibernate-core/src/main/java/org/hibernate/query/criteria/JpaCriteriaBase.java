/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.CommonAbstractCriteria;
import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaCriteriaBase extends CommonAbstractCriteria, JpaCriteriaNode {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<U> JpaSubQuery<U> subquery(@Nonnull Class<U> type);

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaPredicate getRestriction();
}
