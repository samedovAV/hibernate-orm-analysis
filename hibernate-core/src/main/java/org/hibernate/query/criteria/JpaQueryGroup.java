/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.query.common.FetchClauseType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A query group i.e. query parts connected with a set operator.
 *
 * @author Christian Beikov
 */
public interface JpaQueryGroup<T> extends JpaQueryPart<T> {

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaQueryPart<T>> getQueryParts();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant overrides

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryGroup<T> setSortSpecifications(@Nonnull List<? extends JpaOrder> sortSpecifications);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryGroup<T> setOffset(@Nonnull JpaExpression<? extends Number> offset);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryGroup<T> setFetch(@Nullable JpaExpression<? extends Number> fetch);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryGroup<T> setFetch(@Nullable JpaExpression<? extends Number> fetch, FetchClauseType fetchClauseType);

}
