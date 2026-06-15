/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.PluralAttribute;

import org.hibernate.metamodel.CollectionClassification;
import org.hibernate.query.NotIndexedCollectionException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension of the JPA-defined {@link PluralAttribute} interface.
 *
 * @author Steve Ebersole
 */
public interface PluralPersistentAttribute<D, C, E>
		extends PersistentAttribute<D, C>, PathSource<E>, PluralAttribute<D, C, E> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<D> getDeclaringType();

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionClassification getCollectionClassification();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PathSource<E> getElementPathSource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PathSource<?> getIndexPathSource() {
		throw new NotIndexedCollectionException(
				"Plural attribute [" +  getPathName() + "] is not indexed (list / map)"
		);
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<E> getElementType();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<E> getValueGraphType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SimpleDomainType<?> getKeyGraphType() {
		throw new NotIndexedCollectionException(
				"Plural attribute [" +  getPathName() + "] is not indexed (list / map)"
		);
	}
}
