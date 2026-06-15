/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.sqm.tree.domain.SqmTreatedEntityJoin;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaEntityJoin<L,R> extends JpaJoin<L,R> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<R> getModel();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedEntityJoin<L,R,S> treatAs(@Nonnull Class<S> treatAsType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R> SqmTreatedEntityJoin<L,R,S> treatAs(@Nonnull EntityDomainType<S> treatAsType);
}
