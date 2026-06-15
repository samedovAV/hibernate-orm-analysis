/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractRestrictedTableMutation<O extends MutationOperation>
		extends AbstractTableMutation<O>
		implements RestrictedTableMutation<O> {
	private final List<ColumnValueBinding> keyRestrictionBindings;
	private final List<ColumnValueBinding> optLockRestrictionBindings;

	public AbstractRestrictedTableMutation(
			MutatingTableReference mutatingTable,
			MutationTarget<?,?> mutationTarget,
			String comment,
			List<ColumnValueBinding> keyRestrictionBindings,
			List<ColumnValueBinding> optLockRestrictionBindings,
			List<ColumnValueParameter> parameters) {
		super( mutatingTable, mutationTarget, comment, parameters );
		this.keyRestrictionBindings = keyRestrictionBindings;
		this.optLockRestrictionBindings = optLockRestrictionBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnValueBinding> getKeyBindings() {
		return keyRestrictionBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachKeyBinding(BiConsumer<Integer, ColumnValueBinding> consumer) {
		forEachThing( keyRestrictionBindings, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ColumnValueBinding> getOptimisticLockBindings() {
		return optLockRestrictionBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forEachOptimisticLockBinding(BiConsumer<Integer, ColumnValueBinding> consumer) {
		forEachThing( optLockRestrictionBindings, consumer );
	}
}
