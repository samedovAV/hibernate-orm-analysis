/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.internal;

import java.util.List;
import java.util.function.BiConsumer;

import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.ast.MutationGroup;
import org.hibernate.sql.model.ast.TableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard MutationGroup implementation for cases with multiple table mutations
 *
 * @author Steve Ebersole
 */
public class MutationGroupStandard implements MutationGroup {
	private final MutationType mutationType;
	private final MutationTarget<?,?> mutationTarget;
	private final List<? extends TableMutation<?>> tableMutationList;

	public MutationGroupStandard(
			MutationType mutationType,
			MutationTarget<?,?> mutationTarget,
			List<? extends TableMutation<?>> tableMutationList) {
		this.mutationType = mutationType;
		this.mutationTarget = mutationTarget;
		this.tableMutationList = tableMutationList;
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
		return tableMutationList.size();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMutation getSingleTableMutation() {
		throw new IllegalStateException( "Group contains multiple table mutations : " + mutationTarget.getNavigableRole() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableMutation getTableMutation(String tableName) {
		for ( int i = 0; i < tableMutationList.size(); i++ ) {
			final TableMutation<?> tableMutation = tableMutationList.get( i );
			if ( tableMutation != null ) {
				if ( tableMutation.getMutatingTable().getTableName().equals( tableName ) ) {
					return tableMutation;
				}
			}
		}
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public <O extends MutationOperation, M extends TableMutation<O>> void forEachTableMutation(BiConsumer<Integer, M> action) {
		for ( int i = 0; i < tableMutationList.size(); i++ ) {
			//noinspection unchecked
			action.accept( i, (M)tableMutationList.get( i ) );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMutation getTableMutation(int i) {
		return tableMutationList.get( i );
	}

}
