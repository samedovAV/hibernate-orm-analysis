/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.jpa;

import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import java.util.List;

import org.hibernate.query.criteria.JpaSelection;
import org.hibernate.query.sqm.NodeBuilder;
import org.hibernate.query.sqm.SqmBindableType;
import org.hibernate.query.sqm.tree.select.SqmSelectableNode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Base support for {@link JpaSelection} impls.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractJpaSelection<T>
		extends AbstractJpaTupleElement<T>
		implements SqmSelectableNode<T>, JpaSelection<T> {
	protected AbstractJpaSelection(@Nullable SqmBindableType<? super T> sqmExpressible, NodeBuilder criteriaBuilder) {
		super( sqmExpressible, criteriaBuilder );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JpaSelection<T> alias(@Nonnull String alias) {
		setAlias( alias );
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCompoundSelection() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<? extends JpaSelection<?>> getSelectionItems() {
		throw new IllegalStateException( "Not a compound selection" );
	}
}
