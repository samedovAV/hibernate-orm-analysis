/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.spi;

import org.hibernate.query.sqm.tree.expression.SqmParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the context in which a parameter is declared.  This is  used mainly to
 * determine metadata about the parameter ({@link SqmParameter#allowMultiValuedBinding()}, e.g.)
 *
 * @author Steve Ebersole
 */
public interface ParameterDeclarationContext {
	/**
	 * Are multi-valued parameter bindings allowed in this context?
	 *
	 * @return {@code true} if they are; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMultiValuedBindingAllowed();
}
