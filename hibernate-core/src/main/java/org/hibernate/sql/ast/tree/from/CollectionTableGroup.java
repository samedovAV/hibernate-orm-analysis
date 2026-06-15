/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAliasBase;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A table group for collection tables of plural attributes.
 *
 * @author Christian Beikov
 */
public class CollectionTableGroup extends StandardTableGroup implements PluralTableGroup {

	private TableGroup indexTableGroup;
	private TableGroup elementTableGroup;

	public CollectionTableGroup(
			boolean canUseInnerJoins,
			NavigablePath navigablePath,
			PluralAttributeMapping tableGroupProducer,
			boolean fetched,
			String sourceAlias,
			TableReference primaryTableReference,
			boolean realTableGroup,
			SqlAliasBase sqlAliasBase,
			Predicate<String> tableReferenceJoinNameChecker,
			BiFunction<String, TableGroup, TableReferenceJoin> tableReferenceJoinCreator,
			SessionFactoryImplementor sessionFactory) {
		super(
				canUseInnerJoins,
				navigablePath,
				tableGroupProducer,
				fetched,
				sourceAlias,
				primaryTableReference,
				realTableGroup,
				sqlAliasBase,
				tableReferenceJoinNameChecker,
				tableReferenceJoinCreator,
				sessionFactory
		);
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

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerElementTableGroup(TableGroupJoin elementTableGroupJoin) {
		registerElementTableGroup( elementTableGroupJoin, true );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerElementTableGroup(TableGroupJoin elementTableGroupJoin, boolean nested) {
		assert this.elementTableGroup == null;
		this.elementTableGroup = elementTableGroupJoin.getJoinedGroup();
		if ( nested ) {
			addNestedTableGroupJoin( elementTableGroupJoin );
		}
		else {
			addTableGroupJoin( elementTableGroupJoin );
		}
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
