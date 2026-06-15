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
 * Models a query part i.e. the commonalities between a query group and a query specification.
 *
 * @see JpaQueryStructure
 * @see JpaQueryGroup
 *
 * @author Christian Beikov
 */
public interface JpaQueryPart<T> extends JpaCriteriaNode {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Ordering clause

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaOrder> getSortSpecifications();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryPart<T> setSortSpecifications(@Nonnull List<? extends JpaOrder> sortSpecifications);


	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Limit/Offset/Fetch clause

	//TODO: these operations should only accept integer literals or parameters

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<? extends Number> getOffset();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryPart<T> setOffset(JpaExpression<? extends Number> offset);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaExpression<? extends Number> getFetch();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryPart<T> setFetch(@Nullable JpaExpression<? extends Number> fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaQueryPart<T> setFetch(@Nullable JpaExpression<? extends Number> fetch, FetchClauseType fetchClauseType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchClauseType getFetchClauseType();
}
