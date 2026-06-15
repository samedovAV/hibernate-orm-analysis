/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Set;


import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.MappingMetamodel;
import org.hibernate.type.descriptor.java.EnumJavaType;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extensions to the JPA-defined {@linkplain Metamodel metamodel} of
 * persistent Java types.
 *
 * @apiNote This is an incubating API. Its name and package may change.
 *
 * @see MappingMetamodel
 *
 * @since 6.0
 * @author Steve Ebersole
 */
@Incubating
public interface JpaMetamodel extends Metamodel {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Extended features

	/**
	 * Access to a managed type through its name
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<?> managedType(@Nullable String typeName);

	/**
	 * Access to an entity supporting Hibernate's entity-name feature
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<?> entity(@Nullable String entityName);

	/**
	 * Access to an embeddable type from FQN
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableDomainType<?> embeddable(@Nullable String embeddableName);

	/**
	 * Specialized handling for resolving entity-name references in
	 * an HQL query
	 */

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<?> getHqlEntityReference(@Nonnull String entityName);

	/**
	 * Specialized handling for resolving entity-name references in
	 * an HQL query
	 */
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<?> resolveHqlEntityReference(@Nonnull String entityName);

	/**
	 * Same as {@link #managedType(Class)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> ManagedDomainType<X> findManagedType(Class<X> cls);

	/**
	 * Same as {@link #entity(Class)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> EntityDomainType<X> findEntityType(Class<X> cls);

	/**
	 * Same as {@link #embeddable(Class)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> EmbeddableDomainType<X> findEmbeddableType(Class<X> cls);

	/**
	 * Same as {@link #managedType(String)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedDomainType<?> findManagedType(@Nullable String typeName);

	/**
	 * Same as {@link #entity(String)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityDomainType<?> findEntityType(@Nullable String entityName);

	/**
	 * Same as {@link #embeddable(String)} except {@code null} is returned rather
	 * than throwing an exception
	 */
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EmbeddableDomainType<?> findEmbeddableType(@Nullable String embeddableName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String qualifyImportableName(String queryName);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Enumerations and Java constants (useful for interpreting HQL)

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getEnumTypesForValue(String enumValue);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EnumJavaType<?> getEnumType(String className);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E extends Enum<E>> E enumValue(EnumJavaType<E> enumType, String enumValueName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JavaType<?> getJavaConstantType(String className, String fieldName);

	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E> E getJavaConstant(String className, String fieldName, Class<E> javaTypeClass);

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant returns

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> ManagedDomainType<X> managedType(@Nonnull Class<X> cls);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> EntityDomainType<X> entity(@Nonnull Class<X> cls);

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> EmbeddableDomainType<X> embeddable(@Nonnull Class<X> cls);

}
