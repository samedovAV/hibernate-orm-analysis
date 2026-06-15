/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.dialect.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for the source of {@link DialectResolutionInfo}.
 */
@FunctionalInterface
public interface DialectResolutionInfoSource {
	/**
	 * Get the DialectResolutionInfo
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DialectResolutionInfo getDialectResolutionInfo();
}
