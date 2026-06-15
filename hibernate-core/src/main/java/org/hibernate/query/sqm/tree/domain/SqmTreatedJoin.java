/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.criteria.JpaTreatedJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmTreatedJoin<L,R,R1 extends R> extends SqmTreatedFrom<L,R,R1>, JpaTreatedJoin<L,R,R1> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> SqmTreatedJoin<L, R1, S> treatAs(@Nonnull Class<S> treatJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> SqmTreatedJoin<L, R1, S> treatAs(@Nonnull EntityDomainType<S> treatTarget);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> SqmTreatedJoin<L, R1, S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> SqmTreatedJoin<L, R1, S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias);
}
