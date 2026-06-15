/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm;

import java.util.Locale;

import jakarta.persistence.metamodel.Bindable;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.PathSource;
import org.hibernate.query.sqm.tree.domain.SqmDomainType;
import org.hibernate.query.sqm.tree.SqmExpressibleAccessor;
import org.hibernate.query.sqm.tree.domain.SqmPath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents any part of the domain model which can be used to create a
 * {@link SqmPath} node.
 *
 * @apiNote Parallel to the JPA-defined interface
 *          {@link jakarta.persistence.metamodel.Bindable}
 *          but broader mainly to support {@code @Any} mappings
 *
 * @author Steve Ebersole
 */
public interface SqmPathSource<J>
		extends SqmExpressible<J>, Bindable<J>, SqmExpressibleAccessor<J>, PathSource<J> {

	/**
	 * The type of {@linkplain SqmPath path} this source creates.
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmDomainType<J> getPathType();

	/**
	 * Find a {@link SqmPathSource} by name relative to this source.
	 *
	 * @param name the name of the path source to find
	 * @return null if the subPathSource is not found
	 * @throws IllegalStateException to indicate that this source cannot be de-referenced
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<?> findSubPathSource(String name);

	/**
	 * Find a {@link SqmPathSource} by name relative to this source. If {@code includeSubtypes} is set
	 * to {@code true} and this path source is polymorphic, also try finding subtype attributes.
	 *
	 * @param name the name of the path source to find
	 * @param includeSubtypes flag indicating whether to consider subtype attributes
	 * @return null if the subPathSource is not found
	 * @throws IllegalStateException to indicate that this source cannot be de-referenced
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default @Nullable SqmPathSource<?> findSubPathSource(String name, boolean includeSubtypes) {
		return findSubPathSource( name );
	}

	/**
	 * Find a {@link SqmPathSource} by name relative to this source.
	 *
	 * @param name the name of the path source to find
	 * @throws IllegalStateException to indicate that this source cannot be de-referenced
	 * @throws IllegalArgumentException if the subPathSource is not found
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmPathSource<?> getSubPathSource(String name) {
		final SqmPathSource<?> subPathSource = findSubPathSource( name );
		if ( subPathSource == null ) {
			throw new PathElementException(
					String.format(
							Locale.ROOT,
							"Could not resolve attribute '%s' of '%s'",
							name,
							getExpressible().getTypeName()
					)
			);
		}
		return subPathSource;
	}

	/**
	 * Find a {@link SqmPathSource} by name relative to this source. If {@code subtypes} is set
	 * to {@code true} and this path source is polymorphic, also try finding subtype attributes.
	 *
	 * @param name the name of the path source to find
	 * @param subtypes flag indicating whether to consider subtype attributes
	 * @throws IllegalStateException to indicate that this source cannot be de-referenced
	 * @throws IllegalArgumentException if the subPathSource is not found
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqmPathSource<?> getSubPathSource(String name, boolean subtypes) {
		final var subPathSource = findSubPathSource( name, subtypes );
		if ( subPathSource == null ) {
			throw new PathElementException(
					String.format(
							Locale.ROOT,
							"Could not resolve attribute '%s' of '%s'",
							name,
							getExpressible().getTypeName()
					)
			);
		}
		return subPathSource;
	}

	/**
	 * Returns the intermediate {@link SqmPathSource} for a path source
	 * previously acquired via {@link #findSubPathSource(String)}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable SqmPathSource<?> getIntermediatePathSource(SqmPathSource<?> pathSource) {
		return null;
	}

	/**
	 * Create an SQM path for this source relative to the given left hand side
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPath<J> createSqmPath(SqmPath<?> lhs, @Nullable SqmPathSource<?> intermediatePathSource);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nonnull SqmBindableType<J> getExpressible() {
		return getPathType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable SqmDomainType<J> getSqmType() {
		return getPathType();
	}

	/**
	 * Indicates if this path source is generically typed
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isGeneric() {
		return false;
	}
}
