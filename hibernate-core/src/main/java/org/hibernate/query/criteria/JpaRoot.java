/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Root;

import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JpaRoot<T> extends JpaFrom<T,T>, Root<T> {
	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<T> getModel();

	@Nonnull
	@Override
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <S extends T> JpaRoot<S> treat(@Nonnull Class<S> treatJavaType) {
		return (JpaRoot<S>) treatAs( treatJavaType );
	}

	// todo: deprecate and remove?
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<T> getManagedType();
}
