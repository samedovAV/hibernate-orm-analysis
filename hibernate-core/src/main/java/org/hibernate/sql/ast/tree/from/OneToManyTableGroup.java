/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A table group for one-to-many plural attributes.
 * Delegates by default to the element table group,
 * but also provides access to the index table group table references.
 *
 * @author Christian Beikov
 */
public class OneToManyTableGroup extends AbstractColumnReferenceQualifier implements TableGroup, PluralTableGroup {
	private final SessionFactoryImplementor sessionFactory;
	private final PluralAttributeMapping pluralAttributeMapping;
	private final TableGroup elementTableGroup;
	private TableGroup indexTableGroup;

	public OneToManyTableGroup(
			PluralAttributeMapping pluralAttributeMapping,
			TableGroup elementTableGroup,
			SessionFactoryImplementor sessionFactory) {
		this.pluralAttributeMapping = pluralAttributeMapping;
		this.elementTableGroup = elementTableGroup;
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getExpressionType() {
		return pluralAttributeMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeMapping getModelPart() {
		return pluralAttributeMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SessionFactoryImplementor getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup getElementTableGroup() {
		return elementTableGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup getIndexTableGroup() {
		return indexTableGroup;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerIndexTableGroup(TableGroupJoin indexTableGroupJoin) {
		registerIndexTableGroup( indexTableGroupJoin, true );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerIndexTableGroup(TableGroupJoin indexTableGroupJoin, boolean nested) {
		assert this.indexTableGroup == null;
		this.indexTableGroup = indexTableGroupJoin.getJoinedGroup();
		if ( nested ) {
			addNestedTableGroupJoin( indexTableGroupJoin );
		}
		else {
			addTableGroupJoin( indexTableGroupJoin );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getGroupAlias() {
		return elementTableGroup.getGroupAlias();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSourceAlias() {
		return elementTableGroup.getSourceAlias();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
		elementTableGroup.applyAffectedTableNames( nameCollector );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return elementTableGroup.getPrimaryTableReference();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return elementTableGroup.getTableReferenceJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return elementTableGroup.getNavigablePath().getParent();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableGroupJoin> getTableGroupJoins() {
		return elementTableGroup.getTableGroupJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableGroupJoin> getNestedTableGroupJoins() {
		return elementTableGroup.getNestedTableGroupJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean canUseInnerJoins() {
		return elementTableGroup.canUseInnerJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addTableGroupJoin(TableGroupJoin join) {
		if ( join.getJoinedGroup() != elementTableGroup ) {
			elementTableGroup.addTableGroupJoin( join );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void prependTableGroupJoin(NavigablePath navigablePath, TableGroupJoin join) {
		if ( join.getJoinedGroup() != elementTableGroup ) {
			elementTableGroup.prependTableGroupJoin( navigablePath, join );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addNestedTableGroupJoin(TableGroupJoin join) {
		if ( join.getJoinedGroup() != elementTableGroup ) {
			elementTableGroup.addNestedTableGroupJoin( join );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitTableGroupJoins(Consumer<TableGroupJoin> consumer) {
		elementTableGroup.visitTableGroupJoins( consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNestedTableGroupJoins(Consumer<TableGroupJoin> consumer) {
		elementTableGroup.visitNestedTableGroupJoins( consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResult<?> createDomainResult(String resultVariable, DomainResultCreationState creationState) {
		return elementTableGroup.createDomainResult( resultVariable, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		elementTableGroup.applySqlSelections( creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isRealTableGroup() {
		return elementTableGroup.isRealTableGroup();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFetched() {
		return elementTableGroup.isFetched();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		final TableReference tableReference = elementTableGroup.getTableReference(
				navigablePath,
				tableExpression,
				resolve
		);
		if ( tableReference != null || indexTableGroup == null
				|| navigablePath != null && indexTableGroup.getNavigablePath().isParent( navigablePath ) ) {
			return tableReference;
		}

		return indexTableGroup.getTableReference(
				navigablePath,
				tableExpression,
				resolve
		);
	}
}
