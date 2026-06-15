/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.metamodel.EntityType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaCriteriaDelete<T> extends JpaManipulationCriteria<T>, CriteriaDelete<T> {

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> from(@Nonnull Class<T> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> from(@Nonnull EntityType<T> entity);

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> getRoot();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaDelete<T> where(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaDelete<T> where(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaDelete<T> where(@Nonnull List<? extends Expression<Boolean>> restrictions);
}
