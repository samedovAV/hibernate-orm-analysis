/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.metamodel.MapAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Hibernate extension to the JPA {@link MapAttribute} descriptor
 *
 * @author Steve Ebersole
 */
public interface MapPersistentAttribute<D,K,V> extends MapAttribute<D, K, V>, PluralPersistentAttribute<D,Map<K,V>,V> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PathSource<K> getKeyPathSource();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<K> getKeyType();

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<K> getKeyGraphType();
}
