/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal.options;

import jakarta.persistence.CacheRetrieveMode;
import jakarta.persistence.CacheStoreMode;
import org.hibernate.CacheMode;
import org.hibernate.ConnectionAcquisitionMode;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.Interceptor;
import org.hibernate.SessionCreationOption;
import org.hibernate.StatementObserver;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.EmptyInterceptor;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.sql.Connection;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyList;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Mutable collector for options common to creation of both stateful
/// and stateless sessions.
///
/// Instances are populated by session builders and by the Jakarta
/// Persistence type-safe creation option support, then consumed through
/// the [org.hibernate.engine.creation.internal.SessionCreationOptions]
/// contract when constructing a session.
///
/// The constructor initializes the collector with the defaults supplied
/// by the [SessionFactoryImplementor], but the factory is not retained.
/// Behaviors which require the factory at consumption time, such as
/// interceptor resolution, take it explicitly.
///
/// @since 8.0
/// @author Steve Ebersole
public class CommonOptions {
	protected StatementInspector statementInspector;
	protected StatementObserver statementObserver;
	protected Interceptor interceptor;
	protected boolean allowInterceptor = true;
	protected boolean allowSessionInterceptorCreation = true;
	protected Connection connection;
	protected PhysicalConnectionHandlingMode connectionHandlingMode;
	protected Object tenantIdentifier;
	protected boolean readOnly;
	protected Integer jdbcBatchSize;
	protected CacheMode cacheMode;
	protected TimeZone jdbcTimeZone;
	protected Object temporalIdentifier;
	protected List<SessionCreationOption.EnabledFilter> enabledFilterOptions;
	private int defaultBatchFetchSize;
	private boolean subselectFetchEnabled;

	public CommonOptions(SessionFactoryImplementor sessionFactory) {
		final var options = sessionFactory.getSessionFactoryOptions();
		statementInspector = options.getStatementInspector();
		cacheMode = options.getInitialSessionCacheMode();
		connectionHandlingMode = options.getPhysicalConnectionHandlingMode();
		jdbcTimeZone = options.getJdbcTimeZone();
		tenantIdentifier = sessionFactory.resolveTenantIdentifier();
		defaultBatchFetchSize = options.getDefaultBatchFetchSize();
		subselectFetchEnabled = options.isSubselectFetchEnabled();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StatementInspector getStatementInspector() {
		return statementInspector;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public StatementObserver getStatementObserver() {
		return statementObserver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Connection getConnection() {
		return connection;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PhysicalConnectionHandlingMode getPhysicalConnectionHandlingMode() {
		return connectionHandlingMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getTenantIdentifierValue() {
		return tenantIdentifier;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isReadOnly() {
		return readOnly;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getJdbcBatchSize() {
		return jdbcBatchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheMode getInitialCacheMode() {
		return cacheMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TimeZone getJdbcTimeZone() {
		return jdbcTimeZone;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getTemporalIdentifier() {
		return temporalIdentifier;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<SessionCreationOption.EnabledFilter> getEnabledFilterOptions() {
		return enabledFilterOptions == null ? emptyList() : enabledFilterOptions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void connection(Connection connection) {
		this.connection = connection;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void connectionHandling(ConnectionAcquisitionMode acquisitionMode, ConnectionReleaseMode releaseMode) {
		this.connectionHandlingMode = PhysicalConnectionHandlingMode.interpret( acquisitionMode, releaseMode );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void connectionHandlingMode(PhysicalConnectionHandlingMode connectionHandlingMode) {
		this.connectionHandlingMode = connectionHandlingMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void interceptor(Interceptor interceptor) {
		if ( interceptor == null ) {
			noInterceptor();
		}
		else {
			this.interceptor = interceptor;
			this.allowInterceptor = true;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void useInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
		this.allowInterceptor = true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void noInterceptor() {
		this.interceptor = null;
		this.allowInterceptor = false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void noSessionInterceptorCreation() {
		this.allowSessionInterceptorCreation = false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void tenantIdentifier(Object tenantIdentifier) {
		this.tenantIdentifier = tenantIdentifier;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void jdbcBatchSize(int batchSize) {
		this.jdbcBatchSize = batchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void readOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initialCacheMode(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cacheStoreMode(CacheStoreMode cacheStoreMode) {
		initialCacheMode( CacheMode.interpretStoreMode( cacheMode, cacheStoreMode ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void cacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {
		initialCacheMode( CacheMode.interpretRetrieveMode( cacheMode, cacheRetrieveMode ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void statementInspector(UnaryOperator<String> operator) {
		if ( operator == null ) {
			noStatementInspector();
		}
		else {
			this.statementInspector = operator::apply;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void statementInspector(StatementInspector statementInspector) {
		this.statementInspector = statementInspector;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void noStatementInspector() {
		this.statementInspector = null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void statementObserver(StatementObserver statementObserver) {
		this.statementObserver = statementObserver;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void jdbcTimeZone(TimeZone timeZone) {
		jdbcTimeZone = timeZone;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void asOf(Instant instant) {
		this.temporalIdentifier = instant;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void atChangeset(Object changesetId) {
		this.temporalIdentifier = changesetId;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void defaultBatchFetchSize(int defaultBatchFetchSize) {
		this.defaultBatchFetchSize = defaultBatchFetchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void subselectFetchEnabled(boolean subselectFetchEnabled) {
		this.subselectFetchEnabled = subselectFetchEnabled;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSubselectFetchEnabled() {
		return subselectFetchEnabled;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getDefaultBatchFetchSize() {
		return defaultBatchFetchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void enableFilter(SessionCreationOption.EnabledFilter enabledFilter) {
		if ( enabledFilterOptions == null ) {
			enabledFilterOptions = new ArrayList<>();
		}
		enabledFilterOptions.add( enabledFilter );
	}

	/// Resolve the interceptor to use for the session being created.
	///
	/// This is intentionally not a simple getter. Resolution must account for
	/// an explicitly disabled interceptor, an explicitly supplied interceptor,
	/// the factory-scoped interceptor, and the factory's session-scoped
	/// interceptor supplier.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Interceptor resolveInterceptor(SessionFactoryImplementor sessionFactory) {
		if ( !allowInterceptor ) {
			return null;
		}

		if ( interceptor != null && interceptor != EmptyInterceptor.INSTANCE ) {
			return interceptor;
		}

		final var options = sessionFactory.getSessionFactoryOptions();

		final var optionsInterceptor = options.getInterceptor();
		if ( optionsInterceptor != null && optionsInterceptor != EmptyInterceptor.INSTANCE ) {
			return optionsInterceptor;
		}

		if ( allowSessionInterceptorCreation ) {
			final var statelessInterceptorImplementorSupplier =
					options.getStatelessInterceptorImplementorSupplier();
			if ( statelessInterceptorImplementorSupplier != null ) {
				return statelessInterceptorImplementorSupplier.get();
			}
		}

		return null;
	}
}
