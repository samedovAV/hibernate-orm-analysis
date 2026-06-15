/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast.builder;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.TableMapping;
import org.hibernate.sql.model.ast.ColumnValueBinding;
import org.hibernate.sql.model.ast.MutatingTableReference;
import org.hibernate.sql.model.ast.TableInsert;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Base support for TableInsertBuilder implementations
 *
 * @author Steve Ebersole
 */
public abstract class AbstractTableInsertBuilder
		extends AbstractTableMutationBuilder<TableInsert>
		implements TableInsertBuilder {
	private final List<ColumnValueBinding> valueBindingList = new ArrayList<>();
	private List<ColumnValueBinding> lobValueBindingList;

	private String sqlComment;

	public AbstractTableInsertBuilder(
			MutationTarget<?,?> mutationTarget,
			TableMapping table,
			SessionFactoryImplementor sessionFactory) {
		super( MutationType.INSERT, mutationTarget, table, sessionFactory );
		this.sqlComment = "insert for " + mutationTarget.getRolePath();
	}

	public AbstractTableInsertBuilder(
			MutationTarget<?,?> mutationTarget,
			MutatingTableReference tableReference,
			SessionFactoryImplementor sessionFactory) {
		super( MutationType.INSERT, mutationTarget, tableReference, sessionFactory );
		this.sqlComment = "insert for " + mutationTarget.getRolePath();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlComment() {
		return sqlComment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSqlComment(String sqlComment) {
		this.sqlComment = sqlComment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected List<ColumnValueBinding> getValueBindingList() {
		return valueBindingList;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected List<ColumnValueBinding> getLobValueBindingList() {
		return lobValueBindingList;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addColumnAssignment(ColumnValueBinding valueBinding) {
		if ( hasColumnAssignment( valueBinding ) ) {
			return;
		}
		if ( valueBinding.getColumnReference().getJdbcMapping().getJdbcType().isLob()
				&& getJdbcServices().getDialect().forceLobAsLastValue() ) {
			if ( lobValueBindingList == null ) {
				lobValueBindingList = new ArrayList<>();
			}
			lobValueBindingList.add( valueBinding );
		}
		else {
			valueBindingList.add( valueBinding );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addColumnAssignment(SelectableMapping columnMapping) {
		addColumnAssignment( columnMapping, columnMapping.getWriteExpression() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addColumnAssignment(SelectableMapping columnMapping, String assignment) {
		addColumnAssignment( createValueBinding( assignment, columnMapping ) );
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAssignmentBindings() {
		return !valueBindingList.isEmpty() || CollectionHelper.isNotEmpty( lobValueBindingList );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasColumnAssignment(SelectableMapping selectableMapping) {
		return valueBindingList.stream().anyMatch( binding -> binding.matches( selectableMapping ) )
			|| lobValueBindingList != null
					&& lobValueBindingList.stream().anyMatch( binding -> binding.matches( selectableMapping ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private boolean hasColumnAssignment(ColumnValueBinding valueBinding) {
		return valueBindingList.stream().anyMatch( binding -> binding.equals( valueBinding ) )
			|| lobValueBindingList != null
					&& lobValueBindingList.stream().anyMatch( binding -> binding.equals( valueBinding ) );
	}
}
