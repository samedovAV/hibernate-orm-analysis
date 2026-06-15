/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.Incubating;
import org.hibernate.Internal;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.metamodel.mapping.SelectableMappings;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueBindingList;
import org.hibernate.sql.model.ast.RestrictedTableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized builder for building mutations which have a restrictions (aka, {@code where} clause).
 *
 * @author Steve Ebersole
 */
public interface RestrictedTableMutationBuilder<O extends MutationOperation, M extends RestrictedTableMutation<O>> extends TableMutationBuilder<M> {
	/**
	 * Adds a restriction, which is assumed to be based on a selectable that is NOT
	 * a table key, e.g. optimistic locking.
	 *
	 * @apiNote Be sure you know what you are doing before using this method.  Generally
	 * prefer any of the other methods here for adding non-key restrictions.
	 */
	@Internal @Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addNonKeyRestriction(ColumnValueBinding valueBinding);

	/**
	 * Adds a restriction, which is assumed to be based on a selectable that is NOT
	 * a table key, e.g. optimistic locking.
	 *
	 * @apiNote Be sure you know what you are doing before using this method.  Generally
	 * prefer any of the other methods here for adding non-key restrictions.
	 */
	@Internal
	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addNonKeyRestriction(SelectableMapping restrictableMapping) {
		addNonKeyRestriction( restrictableMapping, restrictableMapping.getWriteExpression() );
	}

	/**
	 * Adds a restriction, which is assumed to be based on a selectable that is NOT
	 * a table key, e.g. optimistic locking.
	 *
	 * @apiNote Be sure you know what you are doing before using this method.  Generally
	 * prefer any of the other methods here for adding non-key restrictions.
	 */
	@Internal
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addNonKeyRestriction(SelectableMapping restrictableMapping, String restrictionExpression);

	/**
	 * Add a restriction as long as the selectable is not a formula and is not nullable
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addKeyRestrictions(SelectableMappings selectableMappings) {
		final int jdbcTypeCount = selectableMappings.getJdbcTypeCount();
		for ( int i = 0; i < jdbcTypeCount; i++ ) {
			addKeyRestriction( selectableMappings.getSelectable( i ) );
		}
	}

	/**
	 * Add a restriction as long as the selectable is not a formula and is not nullable
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addKeyRestrictionsLeniently(SelectableMappings selectableMappings) {
		final int jdbcTypeCount = selectableMappings.getJdbcTypeCount();
		for ( int i = 0; i < jdbcTypeCount; i++ ) {
			addKeyRestrictionLeniently( selectableMappings.getSelectable( i ) );
		}
	}

	/**
	 * Add restriction based on non-version optimistically-locked column
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addOptimisticLockRestrictions(SelectableMappings selectableMappings) {
		final int jdbcTypeCount = selectableMappings.getJdbcTypeCount();
		for ( int i = 0; i < jdbcTypeCount; i++ ) {
			addOptimisticLockRestriction( selectableMappings.getSelectable( i ) );
		}
	}

	/**
	 * Add a restriction as long as the selectable is not a formula and is not nullable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addKeyRestriction(SelectableMapping selectableMapping){
		if ( selectableMapping.isNullable() ) {
			return;
		}
		addKeyRestrictionLeniently( selectableMapping );
	}

	/**
	 * Add a restriction as long as the selectable is not a formula
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addKeyRestrictionLeniently(SelectableMapping selectableMapping) {
		if ( selectableMapping.isFormula() ) {
			return;
		}
		addKeyRestrictionBinding( selectableMapping );
	}

	/**
	 * Add a restriction as long as the selectable is not a formula and is not nullable
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addKeyRestrictionBinding(SelectableMapping selectableMapping);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addNullOptimisticLockRestriction(SelectableMapping column);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addNullRestriction(SelectableMapping column) {
		addNullOptimisticLockRestriction( column );
	}

	/**
	 * Add restriction based on non-version optimistically-locked column
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addOptimisticLockRestriction(SelectableMapping selectableMapping);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void addOptimisticLockRestriction(int position, SelectableMapping selectableMapping) {
		addOptimisticLockRestriction(  selectableMapping );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBindingList getKeyRestrictionBindings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnValueBindingList getOptimisticLockBindings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setWhere(String fragment);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addWhereFragment(String fragment);
}
