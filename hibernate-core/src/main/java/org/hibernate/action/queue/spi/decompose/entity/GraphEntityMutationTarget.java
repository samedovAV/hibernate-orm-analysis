/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.decompose.entity;

import java.util.function.Function;

import org.hibernate.Incubating;
import org.hibernate.action.internal.AbstractEntityInsertAction;
import org.hibernate.action.internal.EntityDeleteAction;
import org.hibernate.action.internal.EntityUpdateAction;
import org.hibernate.action.queue.spi.bind.JdbcValueBindings;
import org.hibernate.action.queue.spi.meta.EntityTableDescriptor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.sql.model.GraphMutationTarget;
import org.hibernate.sql.model.ast.builder.TableDeleteBuilder;
import org.hibernate.sql.model.ast.builder.TableInsertBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Entity-specific mutation target for the graph-based action queue.
///
/// Provides entity table information as [EntityTableDescriptor] instances,
/// used by graph-based action decomposers for planning mutation execution.
///
/// @author Steve Ebersole
/// @since 8.0
@Incubating
public interface GraphEntityMutationTarget extends GraphMutationTarget<EntityTableDescriptor> {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType getTargetPart();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityTableDescriptor getIdentifierTableDescriptor();

	/// All table descriptors for this entity
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityTableDescriptor[] getTableDescriptors();

	/// The ModelPart describing the identifier/key for this target
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getIdentifierDescriptor();

	/// The decomposer for INSERT actions
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityActionDecomposer<AbstractEntityInsertAction> getInsertDecomposer();

	/// The decomposer for UPDATE actions
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityActionDecomposer<EntityUpdateAction> getUpdateDecomposer();

	/// The decomposer for DELETE actions
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityActionDecomposer<EntityDeleteAction> getDeleteDecomposer();

	/// Add discriminator column to the insert operation builder
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addDiscriminatorToInsertGroup(Function<String, TableInsertBuilder> insertGroupBuilder) {
	}

	/// Bind discriminator value for insert
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void bindDiscriminatorForInsert(JdbcValueBindings jdbcValueBindings) {
	}

	/// Add discriminator column to the delete operation builder (for the primary table).
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void addDiscriminatorToDelete(TableDeleteBuilder tableDeleteBuilder) {
	}

	/// Bind discriminator value for insert
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void bindDiscriminatorForDelete(JdbcValueBindings jdbcValueBindings) {
	}

	/// Add soft-delete column to the insert operation builder
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addSoftDeleteToInsertGroup(Function<String, TableInsertBuilder> insertGroupBuilder);
}
