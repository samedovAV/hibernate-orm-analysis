/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal;

import java.sql.Connection;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Interceptor;
import org.hibernate.SessionCreationOption;
import org.hibernate.SessionEventListener;
import org.hibernate.StatementObserver;
import org.hibernate.engine.creation.CommonBuilder;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Options, specified through various {@linkplain CommonBuilder builders},
 * used when creating sessions.
 *
 * @author Steve Ebersole
 *
 * @since 7.2
 */
public interface SessionCreationOptions {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldAutoJoinTransactions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FlushMode getInitialSessionFlushMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSubselectFetchEnabled();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getDefaultBatchFetchSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldAutoClose();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldAutoClear();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Connection getConnection();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Interceptor resolveInterceptor(SessionFactoryImplementor sessionFactory);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatementObserver getStatementObserver();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StatementInspector getStatementInspector();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PhysicalConnectionHandlingMode getPhysicalConnectionHandlingMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getTenantIdentifierValue();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isReadOnly();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getJdbcBatchSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheMode getInitialCacheMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIdentifierRollbackEnabled();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TimeZone getJdbcTimeZone();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getTemporalIdentifier();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SessionCreationOption.EnabledFilter> getEnabledFilterOptions();

	/**
	 * @return the full list of SessionEventListener if this was customized,
	 * or null if this Session is being created with the default list.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SessionEventListener> getCustomSessionEventListeners();
}
