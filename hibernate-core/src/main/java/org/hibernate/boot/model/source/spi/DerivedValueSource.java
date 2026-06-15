/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract describing source of a derived value (formula).
 *
 * @author Steve Ebersole
 */
public interface DerivedValueSource extends RelationalValueSource {
	/**
	 * Obtain the expression used to derive the value.
	 *
	 * @return The derived value expression.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExpression();
}
