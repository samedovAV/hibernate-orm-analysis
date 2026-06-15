/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import java.util.Locale;
import java.util.function.BiConsumer;

import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.ast.MutationGroup;
import org.hibernate.sql.model.ast.TableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * MutationGroup implementation for cases where we have a
 * single table operation
 *
 * @author Steve Ebersole
 */
public class MutationGroupSingle implements MutationGroup {
	private final MutationType mutationType;
	private final MutationTarget<?,?> mutationTarget;
	private final TableMutation<?> tableMutation;

	public MutationGroupSingle(
			MutationType mutationType,
			MutationTarget<?,?> mutationTarget,
			TableMutation<?> tableMutation) {
		this.mutationType = mutationType;
		this.mutationTarget = mutationTarget;
		this.tableMutation = tableMutation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutationType getMutationType() {
		return mutationType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutationTarget<?,?> getMutationTarget() {
		return mutationTarget;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getNumberOfTableMutations() {
		return 1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMutation getSingleTableMutation() {
		return tableMutation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMutation getTableMutation(String tableName) {
		assert tableMutation.getMutatingTable().getTableName().equals( tableName );
		return tableMutation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <O extends MutationOperation, M extends TableMutation<O>> void forEachTableMutation(BiConsumer<Integer, M> action) {
		//noinspection unchecked
		action.accept( 0, (M) tableMutation );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMutation getTableMutation(int i) {
		return tableMutation;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return String.format(
				Locale.ROOT,
				"MutationSqlGroup( %s:`%s` )",
				mutationType.name(),
				mutationTarget.getNavigableRole().getFullPath()
		);
	}
}
