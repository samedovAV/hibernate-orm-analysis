/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.cte;

import org.hibernate.query.SortDirection;

import jakarta.persistence.criteria.Nulls;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class SearchClauseSpecification {
	private final CteColumn cteColumn;
	private final SortDirection sortOrder;
	private final Nulls nullPrecedence;

	public SearchClauseSpecification(CteColumn cteColumn, SortDirection sortOrder, Nulls nullPrecedence) {
		this.cteColumn = cteColumn;
		this.sortOrder = sortOrder;
		this.nullPrecedence = nullPrecedence;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CteColumn getCteColumn() {
		return cteColumn;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SortDirection getSortOrder() {
		return sortOrder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nulls getNullPrecedence() {
		return nullPrecedence;
	}
}
