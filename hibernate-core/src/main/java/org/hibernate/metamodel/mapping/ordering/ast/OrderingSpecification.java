/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering.ast;

import jakarta.persistence.criteria.Nulls;
import org.hibernate.query.SortDirection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An individual sort specification in an order-by fragment
 *
 * @author Steve Ebersole
 */
public class OrderingSpecification implements Node {
	private final OrderingExpression orderingExpression;

	private String collation;
	private SortDirection sortOrder = SortDirection.ASCENDING;
	private Nulls nullPrecedence = Nulls.NONE;
	private String orderByValue;

	public OrderingSpecification(OrderingExpression orderingExpression, String orderByValue) {
		this.orderingExpression = orderingExpression;
		this.orderByValue = orderByValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OrderingExpression getExpression() {
		return orderingExpression;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCollation() {
		return collation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCollation(String collation) {
		this.collation = collation;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SortDirection getSortOrder() {
		return sortOrder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSortOrder(SortDirection sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nulls getNullPrecedence() {
		return nullPrecedence;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNullPrecedence(Nulls nullPrecedence) {
		this.nullPrecedence = nullPrecedence;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOrderByValue() {
		return orderByValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOrderByValue(String orderByValue) {
		this.orderByValue = orderByValue;
	}
}
