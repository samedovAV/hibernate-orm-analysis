/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaTreatedFrom<L,R,R1 extends R> extends JpaTreatedPath<R,R1>, JpaFrom<L,R1> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> JpaTreatedFrom<L, R1, S> treatAs(@Nonnull Class<S> treatJavaType);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends R1> JpaTreatedFrom<L, R1, S> treatAs(@Nonnull EntityDomainType<S> treatJavaType);
}
