/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Collection;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.Internal;
import org.hibernate.metamodel.RepresentationMode;

import jakarta.persistence.metamodel.ManagedType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extensions to the JPA-defined {@link ManagedType} contract.
 *
 * @author Steve Ebersole
 */
public interface ManagedDomainType<J> extends DomainType<J>, ManagedType<J> {
	/**
	 * The name of the managed type.
	 *
	 * @apiNote This usually returns the name of the Java class. However, for
	 *          {@linkplain RepresentationMode#MAP dynamic models}, this returns
	 *          the symbolic name since the Java type is {@link java.util.Map}.
	 *
	 * @see #getRepresentationMode()
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();

	/**
	 * The parent {@linkplain JpaMetamodel metamodel}.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaMetamodel getMetamodel();

	/**
	 * The representation mode.
	 *
	 * @return {@link RepresentationMode#POJO POJO} for Java class entities,
	 *         or {@link RepresentationMode#MAP MAP} for dynamic entities.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RepresentationMode getRepresentationMode();

	/**
	 * The Java class of the entity type.
	 */
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Class<J> getJavaType() {
		return getExpressibleJavaType().getJavaTypeClass();
	}

	/**
	 * The descriptor of the supertype of this type.
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<? super J> getSuperType();

	/**
	 * The descriptors of all known managed subtypes of this type.
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<? extends ManagedDomainType<? extends J>> getSubTypes();

	@Internal
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addSubType(@Nonnull ManagedDomainType<? extends J> subType);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitAttributes(@Nonnull Consumer<? super PersistentAttribute<? super J, ?>> action);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitDeclaredAttributes(@Nonnull Consumer<? super PersistentAttribute<J, ?>> action);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<? super J,?> getAttribute(@Nonnull String name);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<J,?> getDeclaredAttribute(@Nonnull String name);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<? super J,?> findAttribute(@Nonnull String name);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<?, ?> findSubTypesAttribute(@Nonnull String name);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularPersistentAttribute<? super J,?> findSingularAttribute(@Nonnull String name);
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralPersistentAttribute<? super J, ?,?> findPluralAttribute(@Nonnull String name);
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<? super J, ?> findConcreteGenericAttribute(@Nonnull String name);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<J,?> findDeclaredAttribute(@Nonnull String name);
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SingularPersistentAttribute<J, ?> findDeclaredSingularAttribute(@Nonnull String name);
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralPersistentAttribute<J, ?, ?> findDeclaredPluralAttribute(@Nonnull String name);
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentAttribute<J, ?> findDeclaredConcreteGenericAttribute(@Nonnull String name);
}
