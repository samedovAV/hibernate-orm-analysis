/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.support;

import org.hibernate.action.queue.spi.ActionQueue;
import org.hibernate.action.queue.spi.ActionQueueFactory;
import org.hibernate.action.queue.internal.GraphBasedActionQueue;
import org.hibernate.action.queue.spi.PlanningOptions;
import org.hibernate.action.queue.spi.QueueType;
import org.hibernate.action.queue.internal.constraint.ConstraintModel;
import org.hibernate.action.queue.internal.constraint.UniqueSlotExtractor;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.persister.entity.EntityPersister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

import static org.hibernate.cfg.FlushSettings.DEFERRABLE_AVOID_BREAK;
import static org.hibernate.cfg.FlushSettings.DEFERRABLE_EDGES_IGNORE;
import static org.hibernate.cfg.FlushSettings.GRAPH_DEFER_IDENTITY_INSERTS;
import static org.hibernate.cfg.FlushSettings.ORDER_BY_FOREIGN_KEY;
import static org.hibernate.cfg.FlushSettings.ORDER_BY_UNIQUE_KEY;
import static org.hibernate.engine.config.spi.StandardConverters.BOOLEAN;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// ActionQueueFactory for building GraphBasedActionQueue instances.
///
/// @author Steve Ebersole
public class GraphBasedActionQueueFactory implements ActionQueueFactory, Serializable {
	private final PlanningOptions planningOptions;
	private final ConstraintModel constraintModel;
	private final Map<String, EntityPersister> entityPersistersByTable;
	private final boolean deferIdentityInserts;

	public GraphBasedActionQueueFactory(SessionFactoryImplementor factory) {
		planningOptions = factory.getGraphPlanningOptions();
		constraintModel = factory.getMappingMetamodel().getConstraintModel();
		entityPersistersByTable = planningOptions.orderByUniqueKeySlots()
				? UniqueSlotExtractor.buildPersisterMap( factory )
				: Map.of();
		deferIdentityInserts = factory.getServiceRegistry()
				.requireService( ConfigurationService.class )
				.getSetting( GRAPH_DEFER_IDENTITY_INSERTS, BOOLEAN, false );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PlanningOptions getPlanningOptions() {
		return planningOptions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ConstraintModel getConstraintModel() {
		return constraintModel;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, EntityPersister> getEntityPersistersByTable() {
		return entityPersistersByTable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean deferIdentityInserts() {
		return deferIdentityInserts;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueueType getConfiguredQueueType() {
		return QueueType.GRAPH;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ActionQueue buildActionQueue(SessionImplementor session) {
		return new GraphBasedActionQueue(
				constraintModel,
				planningOptions,
				entityPersistersByTable,
				deferIdentityInserts,
				session
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ActionQueue deserialize(
			ObjectInputStream ois,
			SessionImplementor session) throws IOException, ClassNotFoundException {
		return GraphBasedActionQueue.deserialize( ois, this, session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static PlanningOptions buildPlanningOptions(ConfigurationService configurationService) {
		var orderByFk = configurationService.getSetting( ORDER_BY_FOREIGN_KEY, BOOLEAN, true );
		var orderByUnique = configurationService.getSetting( ORDER_BY_UNIQUE_KEY, BOOLEAN, true );

		var avoidBreakingDeferrable = configurationService.getSetting( DEFERRABLE_AVOID_BREAK, BOOLEAN, true );
		var ignoreDeferrableEdges = configurationService.getSetting( DEFERRABLE_EDGES_IGNORE, BOOLEAN, true );

		return new PlanningOptions(
				orderByFk,
				orderByUnique,
				avoidBreakingDeferrable,
				ignoreDeferrableEdges,
				PlanningOptions.UniqueCycleStrategy.IGNORE_UNIQUE_EDGES_IN_CYCLES
		);
	}
}
