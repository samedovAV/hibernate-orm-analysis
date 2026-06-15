/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nonnull;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import org.hibernate.query.criteria.JpaTreatedPath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @param <T> The type of the treat source
 * @param <S> The subtype of {@code <T>} that is the treat "target"
 *
 * @author Steve Ebersole
 */
public interface SqmTreatedPath<T, S extends T> extends JpaTreatedPath<T,S>, SqmPathWrapper<T, S> {
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<S> getTreatTarget();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<T> getWrappedPath();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S1 extends S> SqmTreatedPath<S, S1> treatAs(@Nonnull Class<S1> treatJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S1 extends S> SqmTreatedPath<S, S1> treatAs(@Nonnull EntityDomainType<S1> treatTarget);

}
