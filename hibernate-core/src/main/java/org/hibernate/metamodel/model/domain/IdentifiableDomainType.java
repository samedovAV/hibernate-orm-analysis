/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.metamodel.IdentifiableType;
import jakarta.persistence.metamodel.SingularAttribute;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension to the JPA {@link IdentifiableType} contract.
 *
 * @author Steve Ebersole
 */
public interface IdentifiableDomainType<J>
		extends ManagedDomainType<J>, IdentifiableType<J> {

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PathSource<?> getIdentifierDescriptor();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SingularPersistentAttribute<? super J, Y> getId(Class<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SingularPersistentAttribute<J, Y> getDeclaredId(Class<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SingularPersistentAttribute<? super J, Y> getVersion(Class<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SingularPersistentAttribute<J, Y> getDeclaredVersion(Class<Y> type);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<SingularAttribute<? super J, ?>> getIdClassAttributes();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SimpleDomainType<?> getIdType();

	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	IdentifiableDomainType<? super J> getSupertype();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasIdClass();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularPersistentAttribute<? super J,?> findIdAttribute();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitIdClassAttributes(@Nonnull Consumer<SingularPersistentAttribute<? super J,?>> action);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularPersistentAttribute<? super J, ?> findVersionAttribute();

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends PersistentAttribute<? super J, ?>> findNaturalIdAttributes();
}
