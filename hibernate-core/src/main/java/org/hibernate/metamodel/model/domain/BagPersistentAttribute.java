/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.CollectionAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Hibernate extension to the JPA {@link CollectionAttribute} descriptor
 *
 * @author Steve Ebersole
 */
public interface BagPersistentAttribute<D,E>
		extends CollectionAttribute<D,E>, PluralPersistentAttribute<D,Collection<E>,E> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<E> getValueGraphType();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<E> getElementType();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<D> getDeclaringType();
}
