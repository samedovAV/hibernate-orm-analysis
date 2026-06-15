/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.exception.spi;

import java.sql.SQLException;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An object that can extract the name of a violated database constraint
 * from a {@link SQLException} that results from the constraint violation.
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface ViolatedConstraintNameExtractor {
	/**
	 * Extract the name of the violated constraint from the given
	 * {@link SQLException}.
	 *
	 * @param sqle The exception that was the result of the constraint violation.
	 * @return The extracted constraint name.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String extractConstraintName(SQLException sqle);
}
