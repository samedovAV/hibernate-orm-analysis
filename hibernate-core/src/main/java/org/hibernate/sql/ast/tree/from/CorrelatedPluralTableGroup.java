/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.function.Consumer;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.ValuedModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAliasBase;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A table group for correlated plural attributes.
 *
 * @author Christian Beikov
 */
public class CorrelatedPluralTableGroup extends CorrelatedTableGroup implements PluralTableGroup {

	private TableGroup indexTableGroup;
	private TableGroup elementTableGroup;

	public CorrelatedPluralTableGroup(
			TableGroup correlatedTableGroup,
			SqlAliasBase sqlAliasBase,
			QuerySpec querySpec,
			Consumer<Predicate> joinPredicateConsumer,
			SessionFactoryImplementor sessionFactory) {
		super( correlatedTableGroup, sqlAliasBase, querySpec, joinPredicateConsumer, sessionFactory );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public PluralAttributeMapping getModelPart() {
		return (PluralAttributeMapping) super.getModelPart();
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

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerIndexTableGroup(TableGroupJoin indexTableGroupJoin) {
		assert this.indexTableGroup == null;
		this.indexTableGroup = indexTableGroupJoin.getJoinedGroup();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerElementTableGroup(TableGroupJoin elementTableGroupJoin) {
		assert this.elementTableGroup == null;
		this.elementTableGroup = elementTableGroupJoin.getJoinedGroup();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			ValuedModelPart modelPart,
			String tableExpression,
			boolean resolve) {
		final TableReference tableReference = super.getTableReference(
				navigablePath,
				modelPart,
				tableExpression,
				resolve
		);
		if ( tableReference != null ) {
			return tableReference;
		}
		if ( indexTableGroup != null && ( navigablePath == null || indexTableGroup.getNavigablePath().isParent( navigablePath ) ) ) {
			final TableReference indexTableReference = indexTableGroup.getTableReference(
					navigablePath,
					modelPart,
					tableExpression,
					resolve
			);
			if ( indexTableReference != null ) {
				return indexTableReference;
			}
		}
		if ( elementTableGroup != null && ( navigablePath == null || elementTableGroup.getNavigablePath().isParent( navigablePath ) ) ) {
			final TableReference elementTableReference = elementTableGroup.getTableReference(
					navigablePath,
					modelPart,
					tableExpression,
					resolve
			);
			return elementTableReference;
		}
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		final TableReference tableReference = super.getTableReference(
				navigablePath,
				tableExpression,
				resolve
		);
		if ( tableReference != null ) {
			return tableReference;
		}
		if ( indexTableGroup != null && ( navigablePath == null || indexTableGroup.getNavigablePath().isParent( navigablePath ) ) ) {
			final TableReference indexTableReference = indexTableGroup.getTableReference(
					navigablePath,
					tableExpression,
					resolve
			);
			if ( indexTableReference != null ) {
				return indexTableReference;
			}
		}
		if ( elementTableGroup != null && ( navigablePath == null || elementTableGroup.getNavigablePath().isParent( navigablePath ) ) ) {
			final TableReference elementTableReference = elementTableGroup.getTableReference(
					navigablePath,
					tableExpression,
					resolve
			);
			return elementTableReference;
		}
		return null;
	}

}
