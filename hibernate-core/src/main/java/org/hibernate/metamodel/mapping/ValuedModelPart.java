/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes a ModelPart that is also a ValueMapping (and therefore also a SelectableMappings).
 * <p>
 * {@linkplain BasicValuedModelPart Basic} and {@linkplain EmbeddableValuedModelPart embedded}
 * model-parts fall into this category.
 *
 * @author Steve Ebersole
 */
public interface ValuedModelPart extends ModelPart, ValueMapping, SelectableMappings {
	/**
	 * The table which contains the columns mapped by this value
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getContainingTableExpression();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getJdbcTypeCount() {
		return ModelPart.super.getJdbcTypeCount();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcMapping getSingleJdbcMapping() {
		return ModelPart.super.getSingleJdbcMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachSelectable(int offset, SelectableConsumer consumer) {
		return ModelPart.super.forEachSelectable( offset, consumer );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachSelectable(SelectableConsumer consumer) {
		return ModelPart.super.forEachSelectable( consumer );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int forEachColumn(SelectableConsumer consumer) {
		return ModelPart.super.forEachColumn( consumer );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void forEachInsertable(SelectableConsumer consumer) {
		ModelPart.super.forEachSelectable(
				(selectionIndex, selectableMapping) -> {
					if ( selectableMapping.isInsertable() && !selectableMapping.isFormula() ) {
						consumer.accept( selectionIndex, selectableMapping );
					}
				}
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void forEachNonFormula(SelectableConsumer consumer) {
		ModelPart.super.forEachSelectable(
				(selectionIndex, selectableMapping) -> {
					if ( !selectableMapping.isFormula() ) {
						consumer.accept( selectionIndex, selectableMapping );
					}
				}
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void forEachUpdatable(SelectableConsumer consumer) {
		ModelPart.super.forEachSelectable(
				(selectionIndex, selectableMapping) -> {
					if ( selectableMapping.isUpdateable() && !selectableMapping.isFormula() ) {
						consumer.accept( selectionIndex, selectableMapping );
					}
				}
		);
	}

}
