/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.internal.decompose.entity;


import jakarta.annotation.Nullable;
import org.hibernate.action.queue.spi.bind.BindPlan;
import org.hibernate.action.queue.spi.bind.Checkers;
import org.hibernate.action.queue.spi.bind.JdbcValueBindings;
import org.hibernate.action.queue.spi.bind.OperationResultChecker;
import org.hibernate.action.queue.spi.meta.EntityTableDescriptor;
import org.hibernate.action.queue.spi.plan.FlushOperation;
import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;

import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// @author Steve Ebersole
public class ForceVersionBindPlan implements BindPlan, OperationResultChecker {
	private final EntityTableDescriptor tableDescriptor;
	private final EntityPersister persister;
	private final Object entity;
	private final Object entityId;
	private final Object oldVersion;
	private final Object newVersion;

	public ForceVersionBindPlan(
			EntityTableDescriptor tableDescriptor,
			EntityPersister persister,
			Object entity,
			Object entityId,
			Object oldVersion,
			Object newVersion) {
		this.tableDescriptor = tableDescriptor;
		this.persister = persister;
		this.entity = entity;
		this.entityId = entityId;
		this.oldVersion = oldVersion;
		this.newVersion = newVersion;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getEntityId() {
		return entityId;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getEntityInstance() {
		return entity;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void bindValues(
			JdbcValueBindings jdbcValueBindings,
			FlushOperation flushOperation,
			SharedSessionContractImplementor session) {
		jdbcValueBindings.bindAssignment( -1, newVersion, persister.getVersionMapping() );

		final var keyDescriptor = tableDescriptor.keyDescriptor();
		persister.getIdentifierMapping().breakDownJdbcValues(
				entityId,
				(index, jdbcValue, jdbcValueMapping) ->
						jdbcValueBindings.bindValue(
								jdbcValue,
								keyDescriptor.getSelectable( index ).getSelectionExpression(),
								ParameterUsage.RESTRICT
						),
				session
		);
		jdbcValueBindings.bindRestriction( -1, oldVersion, persister.getVersionMapping() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OperationResultChecker getOperationResultChecker() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean checkResult(
			int affectedRowCount,
			int batchPosition,
			String sqlString,
			SessionFactoryImplementor sessionFactory) throws SQLException {
		return Checkers.identifiedResultsCheck(
				tableDescriptor.updateDetails().getExpectation(),
				affectedRowCount,
				batchPosition,
				persister,
				tableDescriptor,
				entityId,
				sqlString,
				sessionFactory
		);
	}
}
