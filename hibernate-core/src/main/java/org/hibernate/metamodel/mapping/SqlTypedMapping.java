/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.engine.jdbc.Size;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models the type of a thing that can be used as an expression in a SQL query
 *
 * @author Christian Beikov
 */
public interface SqlTypedMapping {
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Long getLength();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getArrayLength();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPrecision();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getScale();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getTemporalPrecision();
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isLob() {
		return getJdbcMapping().getJdbcType().isLob();
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getJdbcMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Size toSize() {
		final Size size = new Size();
		size.setArrayLength( getArrayLength() );
		size.setLength( getLength() );
		if ( getTemporalPrecision() != null ) {
			size.setPrecision( getTemporalPrecision() );
		}
		else {
			size.setPrecision( getPrecision() );
		}
		size.setScale( getScale() );
		return size;
	}
}
