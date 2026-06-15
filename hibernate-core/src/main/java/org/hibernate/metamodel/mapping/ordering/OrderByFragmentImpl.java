/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering;

import java.util.List;

import org.hibernate.metamodel.mapping.ordering.ast.OrderingSpecification;
import org.hibernate.sql.ast.spi.SqlAstCreationState;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class OrderByFragmentImpl implements OrderByFragment {
	private final List<OrderingSpecification> fragmentSpecs;

	public OrderByFragmentImpl(List<OrderingSpecification> fragmentSpecs) {
		this.fragmentSpecs = fragmentSpecs;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<OrderingSpecification> getFragmentSpecs() {
		return fragmentSpecs;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public void apply(QuerySpec ast, TableGroup tableGroup, SqlAstCreationState creationState) {
		for ( int i = 0; i < fragmentSpecs.size(); i++ ) {
			final var orderingSpec = fragmentSpecs.get( i );
			orderingSpec.getExpression().apply(
					ast,
					tableGroup,
					orderingSpec.getCollation(),
					orderingSpec.getOrderByValue(),
					orderingSpec.getSortOrder(),
					orderingSpec.getNullPrecedence(),
					creationState
			);
		}
	}
}
