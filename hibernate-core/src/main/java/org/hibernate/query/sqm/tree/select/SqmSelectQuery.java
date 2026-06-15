/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.select;

import jakarta.annotation.Nonnull;
import org.hibernate.query.criteria.JpaSelectCriteria;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.SqmNode;
import org.hibernate.query.sqm.tree.SqmQuery;
import org.hibernate.query.sqm.tree.cte.SqmCteContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common contract between a {@linkplain SqmSelectStatement root} and a
 * {@link SqmSubQuery sub-query}
 *
 * @author Steve Ebersole
 */
public interface SqmSelectQuery<T> extends SqmQuery<T>, JpaSelectCriteria<T>, SqmNode, SqmCteContainer {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmQuerySpec<T> getQuerySpec();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmQueryPart<T> getQueryPart();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSelectQuery<T> distinct(boolean distinct);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmSelectQuery<T> copy(SqmCopyContext context);
}
