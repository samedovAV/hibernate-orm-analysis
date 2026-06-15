/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.entity;

import org.hibernate.engine.spi.EntityKey;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.InitializerParent;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;

import jakarta.annotation.Nullable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Initializer implementation for initializing entity references.
 *
 * @author Steve Ebersole
 */
public interface EntityInitializer<Data extends InitializerData> extends InitializerParent<Data> {

	/**
	 * Get the descriptor for the type of entity being initialized
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getEntityDescriptor();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityPersister getConcreteDescriptor(Data data);

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default EntityPersister getConcreteDescriptor(RowProcessingState rowProcessingState) {
		return getConcreteDescriptor( getData( rowProcessingState ) );
	}

	/**
	 * Get the target entity instance for the currently processing "row".
	 *
	 * @apiNote Calling this method is only valid from the time
	 * {@link #resolveKey(InitializerData)} has been called until {@link #finishUpRow(InitializerData)}
	 * has been called for the currently processing row
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object getTargetInstance(Data data) {
		return getResolvedInstance( data );
	}
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Object getTargetInstance(RowProcessingState rowProcessingState) {
		return getTargetInstance( getData( rowProcessingState ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable EntityKey resolveEntityKeyOnly(RowProcessingState rowProcessingState) {
		final Data data = getData( rowProcessingState );
		resolveKey( data );
		final EntityKey entityKey = rowProcessingState.getSession().generateEntityKey(
				getEntityIdentifier( data ),
				getConcreteDescriptor( data )
		);
		finishUpRow( data );
		return entityKey;
	}

	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getEntityIdentifier(Data data);
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default @Nullable Object getEntityIdentifier(RowProcessingState rowProcessingState) {
		return getEntityIdentifier( getData( rowProcessingState ) );
	}

	/**
	 * Resets the resolved entity registrations by i.e. removing {@link org.hibernate.engine.spi.EntityHolder}.
	 *
	 * @see org.hibernate.sql.results.graph.embeddable.EmbeddableInitializer#resetResolvedEntityRegistrations(RowProcessingState)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void resetResolvedEntityRegistrations(RowProcessingState rowProcessingState) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEntityInitializer() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default EntityInitializer<?> asEntityInitializer() {
		return this;
	}

}
