/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.StatementReference;
import jakarta.persistence.Statement;

import java.util.List;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Extension to the JPA {@linkplain StatementReference} contract.
/// Provides some simple default implementations for methods which
/// we don't care about internally.
/// Also acts as a marker for Hibernate implementors.
///
/// @author Steve Ebersole
///
/// @since 8.0
public interface JpaStatementReference<T> extends JpaReference, StatementReference {

	/// {@inheritDoc}
	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<Class<?>> getParameterTypes() {
		return null;
	}

	/// {@inheritDoc}
	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<String> getParameterNames() {
		return null;
	}

	/// {@inheritDoc}
	@Override
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<Object> getArguments() {
		return null;
	}

	/// {@inheritDoc}
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Set<Statement.Option> getOptions() {
		return Set.of();
	}
}
