/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria;

import java.util.List;
import jakarta.annotation.Nonnull;
import jakarta.persistence.criteria.Selection;

import static java.util.Collections.unmodifiableList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * API extension to the JPA {@link Selection} contract
 *
 * @author Steve Ebersole
 */
public interface JpaSelection<T> extends JpaTupleElement<T>, Selection<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends JpaSelection<?>> getSelectionItems();

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<Selection<?>> getCompoundSelectionItems() {
		return unmodifiableList( getSelectionItems() );
	}

	@Nonnull
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaSelection<T> alias(@Nonnull String name);
}
