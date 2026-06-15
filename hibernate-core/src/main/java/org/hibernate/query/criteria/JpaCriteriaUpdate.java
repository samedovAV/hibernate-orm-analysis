/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.BooleanExpression;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaCriteriaUpdate<T> extends JpaManipulationCriteria<T>, CriteriaUpdate<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isVersioned();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> versioned();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> versioned(boolean versioned);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> from(@Nonnull Class<T> entityClass);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> from(@Nonnull EntityType<T> entity);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y, X extends Y> JpaCriteriaUpdate<T> set(@Nonnull SingularAttribute<? super T, Y> attribute, @Nullable X value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCriteriaUpdate<T> set( @Nonnull SingularAttribute<? super T, Y> attribute, @Nonnull Expression<? extends Y> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y, X extends Y> JpaCriteriaUpdate<T> set(@Nonnull Path<Y> attribute, @Nullable X value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> JpaCriteriaUpdate<T> set(@Nonnull Path<Y> attribute, @Nonnull Expression<? extends Y> value);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> set(@Nonnull String attributeName, @Nullable Object value);

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaRoot<T> getRoot();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> where(@Nonnull Expression<Boolean> restriction);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> where(@Nonnull BooleanExpression... restrictions);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCriteriaUpdate<T> where(@Nonnull List<? extends Expression<Boolean>> restrictions);
}
