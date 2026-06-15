/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

import org.hibernate.metamodel.model.domain.EntityDomainType;
import org.hibernate.query.SemanticException;
import org.hibernate.query.criteria.JpaPath;
import org.hibernate.query.hql.spi.SemanticPathPart;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.ParsingException;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.expression.SqmExpression;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.spi.NavigablePath;
import org.hibernate.type.descriptor.java.JavaType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a reference to a part of the application's domain model as part of an SQM tree.
 *
 * This correlates roughly to the JPA Criteria notion of Path, hence the name.
 *
 * @author Steve Ebersole
 */
public interface SqmPath<T> extends SqmExpression<T>, SemanticPathPart, JpaPath<T> {

	/**
	 * Returns the NavigablePath.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NavigablePath getNavigablePath();

	/**
	 * The path source that this path refers to (and that most likely
	 * created it).
	 *
	 * @see SqmPathSource#createSqmPath
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<T> getReferencedPathSource();

	/**
	 * Retrieve the explicit alias, if one.  May return null
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitAlias();

	/**
	 * Set the explicit alias for this path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setExplicitAlias(@Nullable String explicitAlias);

	/**
	 * Get the left-hand side of this path - may be null, indicating a
	 * root, cross-join or entity-join
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<?> getLhs();

	/**
	 * Returns an immutable List of reusable paths
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SqmPath<?>> getReusablePaths();

	/**
	 * Visit each reusable path relative to this path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitReusablePaths(Consumer<SqmPath<?>> consumer);

	/**
	 * Register a reusable path relative to this path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerReusablePath(SqmPath<?> path);

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<?> getReusablePath(String name);

	/**
	 * This node's type is its "referenced path source"
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<T> getNodeType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void applyInferableType(@Nullable SqmBindableType<?> type) {
		// do nothing
	}

	@Nullable
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default JavaType<T> getJavaTypeDescriptor() {
		return getNodeType().getExpressibleJavaType();
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull Class<S> treatJavaType);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull Class<S> treatJavaType, @Nullable String alias, boolean fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<S extends T> SqmTreatedPath<T,S> treatAs(@Nonnull EntityDomainType<S> treatTarget, @Nullable String alias, boolean fetch);

	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmRoot<?> findRoot() {
		final var lhs = getLhs();
		if ( lhs != null ) {
			return lhs.findRoot();
		}

		throw new ParsingException( "Could not find root" );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<?> resolvePathPart(
			String name,
			boolean isTerminal,
			SqmCreationState creationState);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmPath<?> resolveIndexedAccess(
			SqmExpression<?> selector,
			boolean isTerminal,
			SqmCreationState creationState) {
		throw new SemanticException( "Index operator applied to non-plural path '" + getNavigablePath() + "'" );
	}

	/**
	 * Get this path's actual resolved model, i.e. the concrete type for generic attributes.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<T> getResolvedModel();

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Covariant overrides

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmPath<Y> get(@Nonnull SingularAttribute<? super T, Y> attribute);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<E, C extends Collection<E>> SqmPluralPath<C,E> get(@Nonnull PluralAttribute<? super T, C, E> collection);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<K, V, M extends Map<K, V>> SqmPluralPath<M,V> get(@Nonnull MapAttribute<? super T, K, V> map);

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmExpression<Class<? extends T>> type();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<Y> SqmPath<Y> get(@Nonnull String attributeName);

	/**
	 * Same as {@link #get(String)}, but if {@code includeSubtypes} is set to {@code true}
	 * and this path is polymorphic, also try finding subtype attributes.
	 *
	 * @see SqmPathSource#findSubPathSource(String, boolean)
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default SqmPath<?> get(String attributeName, boolean includeSubtypes) {
		return get( attributeName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<T> copy(SqmCopyContext context);
}
