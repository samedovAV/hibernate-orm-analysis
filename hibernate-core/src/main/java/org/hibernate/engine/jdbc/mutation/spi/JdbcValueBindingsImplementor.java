/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.spi;

import jakarta.annotation.Nullable;
import org.hibernate.engine.jdbc.mutation.JdbcValueBindings;
import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// SPI extension to [JdbcValueBindings] to allow access to bound values.
/// Needed for cycle breaking support.
///
/// @author Steve Ebersole
public interface JdbcValueBindingsImplementor extends JdbcValueBindings {
	/// Retrieve the currently bound value for the given parameter.
	///
	/// @see Binding#getValue()
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getBoundValue(String tableName, String columnName, ParameterUsage usage);

	/// Allow replacing a bound value.
	/// Used by cycle breaking to inject mutable object handles.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void replaceValue(String tableName, String columnName, ParameterUsage usage, Object newValue);
}
