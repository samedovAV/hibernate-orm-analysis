/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import org.hibernate.sql.model.MutationOperation;

import java.util.List;
import java.util.function.BiConsumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Marker interface for TableMutations which assign values - INSERT, UPDATE, MERGE.
///
/// @author Steve Ebersole
public interface AssigningTableMutation<O extends MutationOperation> extends TableMutation<O> {
	/// The number of [value bindings][#getValueBindings].
	///
	/// @see #getValueBindings()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getNumberOfValueBindings() {
		return getValueBindings().size();
	}

	/// The value bindings for each column.
	///
	/// @implNote Table key column(s) are not included here as
	/// those are not ever updated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnValueBinding> getValueBindings();

	/// Visit each [value binding][#getValueBindings]
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void forEachValueBinding(BiConsumer<Integer, ColumnValueBinding> consumer);
}
