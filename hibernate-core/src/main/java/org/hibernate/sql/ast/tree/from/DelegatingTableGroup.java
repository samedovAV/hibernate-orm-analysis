/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.metamodel.mapping.ModelPartContainer;
import org.hibernate.metamodel.mapping.ValuedModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.spi.SqlSelection;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public abstract class DelegatingTableGroup implements TableGroup {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract TableGroup getTableGroup();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ModelPart getExpressionType() {
		return getTableGroup().getExpressionType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Expression getSqlExpression() {
		return getTableGroup().getSqlExpression();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ColumnReference getColumnReference() {
		return getTableGroup().getColumnReference();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SqlSelection createSqlSelection(
			int jdbcPosition,
			int valuesArrayPosition,
			JavaType javaType,
			boolean virtual,
			TypeConfiguration typeConfiguration) {
		return getTableGroup().createSqlSelection(
				jdbcPosition,
				valuesArrayPosition,
				javaType,
				virtual,
				typeConfiguration
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		return getTableGroup().getTableReference( navigablePath, tableExpression, resolve );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			ValuedModelPart modelPart,
			String tableExpression,
			boolean resolve) {
		return getTableGroup().getTableReference( navigablePath, modelPart, tableExpression, resolve );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return getTableGroup().getNavigablePath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getGroupAlias() {
		return getTableGroup().getGroupAlias();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ModelPartContainer getModelPart() {
		return getTableGroup().getModelPart();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSourceAlias() {
		return getTableGroup().getSourceAlias();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableGroupJoin> getTableGroupJoins() {
		return getTableGroup().getTableGroupJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableGroupJoin> getNestedTableGroupJoins() {
		return getTableGroup().getNestedTableGroupJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean canUseInnerJoins() {
		return getTableGroup().canUseInnerJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isLateral() {
		return getTableGroup().isLateral();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addTableGroupJoin(TableGroupJoin join) {
		getTableGroup().addTableGroupJoin( join );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void removeTableGroupJoin(TableGroupJoin join) {
		getTableGroup().removeTableGroupJoin( join );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void prependTableGroupJoin(NavigablePath navigablePath, TableGroupJoin join) {
		getTableGroup().prependTableGroupJoin( navigablePath, join );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addNestedTableGroupJoin(TableGroupJoin join) {
		getTableGroup().addNestedTableGroupJoin( join );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitTableGroupJoins(Consumer<TableGroupJoin> consumer) {
		getTableGroup().visitTableGroupJoins( consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitNestedTableGroupJoins(Consumer<TableGroupJoin> consumer) {
		getTableGroup().visitNestedTableGroupJoins( consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applyAffectedTableNames(Consumer<String> nameCollector) {
		getTableGroup().applyAffectedTableNames( nameCollector );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getPrimaryTableReference() {
		return getTableGroup().getPrimaryTableReference();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<TableReferenceJoin> getTableReferenceJoins() {
		return getTableGroup().getTableReferenceJoins();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResult<?> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState) {
		return getTableGroup().createDomainResult( resultVariable, creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		getTableGroup().applySqlSelections( creationState );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		getTableGroup().accept( sqlTreeWalker );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isRealTableGroup() {
		return getTableGroup().isRealTableGroup();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFetched() {
		return getTableGroup().isFetched();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isInitialized() {
		return getTableGroup().isInitialized();
	}
}
