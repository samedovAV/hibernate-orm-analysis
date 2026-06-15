/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.jdbc.spi;

import org.hibernate.StatementObserver;
import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.stat.spi.StatisticsImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides the "JDBC session" with contextual information it needs during its lifecycle.
 *
 * @author Steve Ebersole
 */
public interface JdbcSessionContext {
	/**
	 * @see org.hibernate.cfg.JdbcSettings#USE_SCROLLABLE_RESULTSET
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isScrollableResultSetsEnabled();

	/**
	 * @see org.hibernate.cfg.JdbcSettings#USE_GET_GENERATED_KEYS
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isGetGeneratedKeysEnabled();

	/**
	 * @see org.hibernate.cfg.JdbcSettings#STATEMENT_FETCH_SIZE
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFetchSizeOrNull();

	/**
	 * @see org.hibernate.cfg.JdbcSettings#CONNECTION_PROVIDER_DISABLES_AUTOCOMMIT
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean doesConnectionProviderDisableAutoCommit();

	/**
	 * @see org.hibernate.cfg.TransactionSettings#PREFER_USER_TRANSACTION
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPreferUserTransaction();

	/**
	 * @see org.hibernate.cfg.TransactionSettings#JTA_TRACK_BY_THREAD
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isJtaTrackByThread();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PhysicalConnectionHandlingMode getPhysicalConnectionHandlingMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatementInspector getStatementInspector();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatementObserver getStatementObserver();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaCompliance getJpaCompliance();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatisticsImplementor getStatistics();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcEventHandler getEventHandler();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcServices getJdbcServices();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BatchBuilder getBatchBuilder();

	/**
	 * @see org.hibernate.resource.transaction.spi.TransactionCoordinatorOwner#isActive()
	 *
	 * @return {@code false} if the session factory was already destroyed
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isActive();
}
