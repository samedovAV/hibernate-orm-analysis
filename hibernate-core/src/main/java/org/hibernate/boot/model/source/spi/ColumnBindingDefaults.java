/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface ColumnBindingDefaults {
	/**
	 * How should non-specification of value insertion by the individual value sources here be
	 * interpreted in terms of defaulting that value.
	 *
	 * @return {@code true} Indicates that insertions are enabled by default for all value sources which
	 * do not explicitly specify.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean areValuesIncludedInInsertByDefault();

	/**
	 * How should non-specification of value updating by the individual value sources here be
	 * interpreted in terms of defaulting that value.
	 *
	 * @return {@code true} Indicates that updates are enabled by default for all value sources which
	 * do not explicitly specify.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean areValuesIncludedInUpdateByDefault();

	/**
	 * How should non-specification of value nullability by the individual value sources here be
	 * interpreted in terms of defaulting that value.
	 *
	 * @return {@code true} Indicates that insertions are enabled by default for all value sources which
	 * do not explicitly specify.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean areValuesNullableByDefault();
}
