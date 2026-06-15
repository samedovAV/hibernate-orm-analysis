/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.entity.mutation;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.AttributeMapping;
import org.hibernate.metamodel.mapping.DiscriminatorValue;
import org.hibernate.metamodel.mapping.SelectableMapping;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.sql.model.MutationOperation;
import org.hibernate.sql.model.ast.builder.AbstractTableUpdateBuilder;
import org.hibernate.sql.model.ast.builder.TableMergeBuilder;
import org.hibernate.sql.model.ast.builder.TableUpdateBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized {@link UpdateCoordinator} for {@code merge into}.
 *
 * @author Gavin King
 */
public class MergeCoordinatorStandard extends UpdateCoordinatorStandard {

	public MergeCoordinatorStandard(EntityPersister entityPersister, SessionFactoryImplementor factory) {
		super( entityPersister, factory );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected <O extends MutationOperation> AbstractTableUpdateBuilder<O> newTableUpdateBuilder(EntityTableMapping tableMapping) {
		final TableMergeBuilder<O> tableUpdateBuilder =
				new TableMergeBuilder<>( entityPersister(), tableMapping, factory() );
		addDiscriminatorValueIfNeeded( tableUpdateBuilder, tableMapping );
		return tableUpdateBuilder;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void addDiscriminatorValueIfNeeded(
			AbstractTableUpdateBuilder<?> tableUpdateBuilder,
			EntityTableMapping tableMapping) {
		final var discriminatorMapping = entityPersister().getDiscriminatorMapping();
		if ( discriminatorMapping != null
				&& discriminatorMapping.hasPhysicalColumn()
				&& tableMapping.getTableName().equals( discriminatorMapping.getContainingTableExpression() ) ) {
			final DiscriminatorValue discriminatorValue = entityPersister().getDiscriminatorValue();
			if ( discriminatorValue != DiscriminatorValue.Special.NULL
				&& discriminatorValue != DiscriminatorValue.Special.NOT_NULL ) {
				tableUpdateBuilder.addValueColumn(
						entityPersister().getDiscriminatorSQLValue(),
						discriminatorMapping
				);
			}
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean isColumnIncludedInSet(SelectableMapping selectable) {
		return selectable.isUpdateable() || selectable.isInsertable();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean isInsertableOrUpdatable(AttributeMapping attribute) {
		final var attributeMetadata = attribute.getAttributeMetadata();
		return attributeMetadata.isUpdatable()
			|| attributeMetadata.isInsertable();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected AttributeInclusionChecker createInclusionChecker(boolean[] attributeUpdateability) {
		return (position, attribute) -> isInsertableOrUpdatable( attribute );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected boolean includeInStaticUpdate(
			int index,
			AttributeMapping attribute,
			boolean[] propertyUpdateability) {
		return isInsertableOrUpdatable( attribute )
			|| super.includeInStaticUpdate( index, attribute, propertyUpdateability );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean includeProperty(boolean[] insertability, boolean[] updateability, int property) {
		return insertability[property] || updateability[property];
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public boolean[] getPropertyUpdateability(Object entity) {
		final boolean[] updateability = super.getPropertyUpdateability( entity );
		final boolean[] insertability = entityPersister().getPropertyInsertability();
		final var result = new boolean[updateability.length];
		for ( int i = 0; i < updateability.length; i++ ) {
			result[i] = updateability[i] || insertability[i];
		}
		return result;
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	public boolean[] getPropertyUpdateability() {
		final boolean[] updateability = entityPersister().getPropertyUpdateability();
		final boolean[] insertability = entityPersister().getPropertyInsertability();
		final var result = new boolean[updateability.length];
		for ( int i = 0; i < updateability.length; i++ ) {
			result[i] = updateability[i] || insertability[i];
		}
		return result;
	}
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void forEachUpdatable(AttributeMapping attributeMapping, TableUpdateBuilder<?> tableUpdateBuilder) {
		attributeMapping.forEachSelectable( tableUpdateBuilder );
	}

	@Override
	@Prove(complexity = Complexity.O_N2, n = "", count = {})
	protected UpdateValuesAnalysisImpl analyzeUpdateValues(
			Object entity,
			Object[] values,
			Object oldVersion,
			Object[] oldValues,
			int[] dirtyAttributeIndexes,
			AttributeInclusionChecker inclusionChecker,
			AttributeInclusionChecker lockingChecker,
			AttributeInclusionChecker dirtinessChecker,
			boolean restrictToTemporalExcluded,
			Object rowId,
			boolean forceDynamicUpdate,
			SharedSessionContractImplementor session) {
		final var updateValuesAnalysis = super.analyzeUpdateValues(
				entity,
				values,
				oldVersion,
				oldValues,
				dirtyAttributeIndexes,
				inclusionChecker,
				lockingChecker,
				dirtinessChecker,
				restrictToTemporalExcluded,
				rowId,
				forceDynamicUpdate,
				session
		);
		if ( oldValues == null ) {
			final TableSet tablesNeedingUpdate = updateValuesAnalysis.getTablesNeedingUpdate();
			final TableSet tablesWithNonNullValues = updateValuesAnalysis.getTablesWithNonNullValues();
			final TableSet tablesWithPreviousNonNullValues = updateValuesAnalysis.getTablesWithPreviousNonNullValues();
			for ( var tableMapping : entityPersister().getTableMappings() ) {
				// Need to upsert into all non-optional table mappings
				if ( !tableMapping.isOptional() ) {
					// If the table was previously not needing an update, remove it from tablesWithPreviousNonNullValues
					// to avoid triggering a delete-statement for this operation
					if ( !tablesNeedingUpdate.contains( tableMapping ) ) {
						tablesWithPreviousNonNullValues.remove( tableMapping );
					}
					tablesNeedingUpdate.add( tableMapping );
					tablesWithNonNullValues.add( tableMapping );
				}
			}
		}
		return updateValuesAnalysis;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "MergeCoordinator(" + entityPersister().getEntityName() + ")";
	}
}
