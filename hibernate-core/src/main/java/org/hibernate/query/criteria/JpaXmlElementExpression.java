/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;

import org.hibernate.Incubating;

import jakarta.persistence.criteria.Expression;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A special expression for the {@code xmlelement} function.
 * @since 7.0
 */
@Incubating
public interface JpaXmlElementExpression extends JpaExpression<String> {

	/**
	 * Passes the given {@link Expression} as value for the XML attribute with the given name.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlElementExpression attribute(String attributeName, Expression<?> expression);

	/**
	 * Passes the given {@link Expression}s as value for the XML content of this element.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlElementExpression content(List<? extends Expression<?>> expressions);

	/**
	 * Passes the given {@link Expression}s as value for the XML content of this element.
	 *
	 * @return {@code this} for method chaining
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaXmlElementExpression content(Expression<?>... expressions);
}
