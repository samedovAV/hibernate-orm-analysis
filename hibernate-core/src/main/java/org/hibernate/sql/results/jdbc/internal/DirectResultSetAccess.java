/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.internal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DirectResultSetAccess extends AbstractResultSetAccess {
	private final PreparedStatement resultSetSource;
	private final ResultSet resultSet;

	public DirectResultSetAccess(
			SharedSessionContractImplementor persistenceContext,
			PreparedStatement resultSetSource,
			ResultSet resultSet) {
		super( persistenceContext );
		this.resultSetSource = resultSetSource;
		this.resultSet = resultSet;

		persistenceContext.getJdbcCoordinator()
				.getLogicalConnection()
				.getResourceRegistry()
				.register( resultSet, resultSetSource );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ResultSet getResultSet() {
		return resultSet;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	protected SessionFactoryImplementor getFactory() {
		return getPersistenceContext().getFactory();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void release() {
		getPersistenceContext().getJdbcCoordinator()
				.getLogicalConnection()
				.getResourceRegistry()
				.release( resultSet, resultSetSource );
	}
}
