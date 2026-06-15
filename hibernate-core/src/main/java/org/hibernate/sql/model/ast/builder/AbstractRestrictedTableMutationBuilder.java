/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.ColumnValueBindingList;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.RestrictedTableMutation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of TableMutationBuilder for mutations which contain a
 * restriction.
 *
 * @author Steve Ebersole
 */
public abstract class AbstractRestrictedTableMutationBuilder<O extends MutationOperation, M extends RestrictedTableMutation<O>>
		extends AbstractTableMutationBuilder<M>
		implements RestrictedTableMutationBuilder<O, M> {

	private final ColumnValueBindingList keyRestrictionBindings;
	private final ColumnValueBindingList optimisticLockBindings;

	public AbstractRestrictedTableMutationBuilder(
			MutationType mutationType,
			MutationTarget<?,?> mutationTarget,
			TableMapping table,
			SessionFactoryImplementor sessionFactory) {
		super( mutationType, mutationTarget, table, sessionFactory );
		this.keyRestrictionBindings = new ColumnValueBindingList( getMutatingTable(), getParameters(), ParameterUsage.RESTRICT );
		this.optimisticLockBindings = new ColumnValueBindingList( getMutatingTable(), getParameters(), ParameterUsage.RESTRICT );
	}

	public AbstractRestrictedTableMutationBuilder(
			MutationType mutationType,
			MutationTarget<?,?> mutationTarget,
			MutatingTableReference tableReference,
			SessionFactoryImplementor sessionFactory) {
		super( mutationType, mutationTarget, tableReference, sessionFactory );
		this.keyRestrictionBindings = new ColumnValueBindingList( getMutatingTable(), getParameters(), ParameterUsage.RESTRICT );
		this.optimisticLockBindings = new ColumnValueBindingList( getMutatingTable(), getParameters(), ParameterUsage.RESTRICT );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBindingList getKeyRestrictionBindings() {
		return keyRestrictionBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ColumnValueBindingList getOptimisticLockBindings() {
		return optimisticLockBindings;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNonKeyRestriction(ColumnValueBinding valueBinding) {
		optimisticLockBindings.addRestriction( valueBinding );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNonKeyRestriction(SelectableMapping restrictableMapping, String restrictionExpression) {
		optimisticLockBindings.addRestriction( ColumnValueBindingBuilder.createValueBinding(
				restrictionExpression,
				restrictableMapping,
				getMutatingTable(),
				ParameterUsage.RESTRICT,
				getParameters()::apply
		) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addKeyRestrictionBinding(SelectableMapping selectableMapping) {
		keyRestrictionBindings.addRestriction( selectableMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addNullOptimisticLockRestriction(SelectableMapping column) {
		optimisticLockBindings.addNullRestriction( column );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addOptimisticLockRestriction(SelectableMapping selectableMapping) {
		optimisticLockBindings.addRestriction( selectableMapping );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setWhere(String fragment) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addWhereFragment(String fragment) {
		throw new UnsupportedOperationException();
	}
}
