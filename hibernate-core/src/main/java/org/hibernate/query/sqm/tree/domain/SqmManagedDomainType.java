/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.ManagedDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface SqmManagedDomainType<J> extends ManagedDomainType<J>, SqmDomainType<J> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Class<J> getJavaType() {
		return ManagedDomainType.super.getJavaType();
	}

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPersistentAttribute<? super J, ?> findAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPersistentAttribute<?, ?> findSubTypesAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmSingularPersistentAttribute<? super J, ?> findSingularAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPluralPersistentAttribute<? super J, ?, ?> findPluralAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPersistentAttribute<J, ?> findDeclaredAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmSingularPersistentAttribute<J, ?> findDeclaredSingularAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPluralPersistentAttribute<J, ?, ?> findDeclaredPluralAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPersistentAttribute<? super J, ?> findConcreteGenericAttribute(@Nonnull String name);

	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmPersistentAttribute<J, ?> findDeclaredConcreteGenericAttribute(@Nonnull String name);
}
