/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import org.hibernate.StatementObserver;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.resource.jdbc.spi.JdbcEventHandler;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.hibernate.stat.spi.StatisticsImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class JdbcSessionContextImpl implements JdbcSessionContext {
	private final SessionFactoryImplementor sessionFactory;
	private final StatementObserver statementObserver;
	private final StatementInspector statementInspector;
	private final PhysicalConnectionHandlingMode connectionHandlingMode;
	private final JdbcServices jdbcServices;
	private final BatchBuilder batchBuilder;

	private final transient JdbcEventHandler jdbcEventHandler;

	JdbcSessionContextImpl(
			SessionFactoryImplementor sessionFactory,
			StatementObserver statementObserver,
			StatementInspector statementInspector,
			PhysicalConnectionHandlingMode connectionHandlingMode,
			JdbcServices jdbcServices,
			BatchBuilder batchBuilder,
			JdbcEventHandler jdbcEventHandler) {
		this.sessionFactory = sessionFactory;
		this.statementObserver = statementObserver != null
				? statementObserver
				: sessionFactory.getStatementObserver();
		this.statementInspector = statementInspector;
		this.connectionHandlingMode = connectionHandlingMode;
		this.jdbcServices = jdbcServices;
		this.batchBuilder = batchBuilder;
		this.jdbcEventHandler = jdbcEventHandler;

		if ( statementInspector == null ) {
			throw new IllegalArgumentException( "StatementInspector cannot be null" );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isScrollableResultSetsEnabled() {
		return settings().isScrollableResultSetsEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isGetGeneratedKeysEnabled() {
		return settings().isGetGeneratedKeysEnabled();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSizeOrNull() {
		return settings().getJdbcFetchSize();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public JpaCompliance getJpaCompliance() {
		return settings().getJpaCompliance();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isPreferUserTransaction() {
		return settings().isPreferUserTransaction();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isJtaTrackByThread() {
		return settings().isJtaTrackByThread();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PhysicalConnectionHandlingMode getPhysicalConnectionHandlingMode() {
		return connectionHandlingMode;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean doesConnectionProviderDisableAutoCommit() {
		return settings().doesConnectionProviderDisableAutoCommit();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StatementObserver getStatementObserver() {
		return statementObserver;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StatementInspector getStatementInspector() {
		return statementInspector;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcEventHandler getEventHandler() {
		return jdbcEventHandler;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private SessionFactoryOptions settings() {
		return sessionFactory.getSessionFactoryOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcServices getJdbcServices() {
		return jdbcServices;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BatchBuilder getBatchBuilder() {
		return batchBuilder;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isActive() {
		return !sessionFactory.isClosed();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public StatisticsImplementor getStatistics() {
		return sessionFactory.getStatistics();
	}
}
