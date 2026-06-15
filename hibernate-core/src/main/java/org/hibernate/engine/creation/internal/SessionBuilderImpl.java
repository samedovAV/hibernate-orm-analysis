/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import org.hibernate.FlushMode;
import org.hibernate.SessionEventListener;
import org.hibernate.engine.creation.internal.options.StatefulOptions;
import org.hibernate.engine.creation.spi.SessionBuilderImplementor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SessionBuilder implementation.
 *
 * @author Steve Ebersole
 */
public abstract class SessionBuilderImpl
		extends AbstractCommonBuilder<SessionBuilderImplementor>
		implements SessionBuilderImplementor {
	private final StatefulOptions options;

	public SessionBuilderImpl(SessionFactoryImplementor sessionFactory) {
		this( sessionFactory, new StatefulOptions( sessionFactory ) );
	}

	protected SessionBuilderImpl(SessionFactoryImplementor sessionFactory, StatefulOptions options) {
		super( sessionFactory, options );
		this.options = options;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected SessionBuilderImplementor getThis() {
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected StatefulOptions options() {
		return options;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// SessionBuilder

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionBuilderImplementor withOption(EntityManager.CreationOption option) {
		options.apply( option );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SessionImplementor openSession() {
		CORE_LOGGER.openingSession( options.getTenantIdentifierValue() );
		return createSession( options );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract SessionImplementor createSession(StatefulOptions options);

	@Override
	@Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor statementInspector(@Nonnull StatementInspector statementInspector) {
		options.statementInspector( statementInspector );
		return this;
	}

	@Override
	@Deprecated
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor connectionHandlingMode(@Nonnull PhysicalConnectionHandlingMode connectionHandlingMode) {
		options.connectionHandlingMode( connectionHandlingMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor autoJoinTransactions(boolean autoJoinTransactions) {
		options.autoJoinTransactions( autoJoinTransactions );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor autoClose(boolean autoClose) {
		options.autoClose( autoClose );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor autoClear(boolean autoClear) {
		options.autoClear( autoClear );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor flushMode(@Nonnull FlushMode flushMode) {
		options.flushMode( flushMode );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor identifierRollback(boolean identifierRollback) {
		options.identifierRollback( identifierRollback );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor eventListeners(@Nonnull SessionEventListener... listeners) {
		options.eventListeners( sessionFactory, listeners );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor clearEventListeners() {
		options.clearEventListeners();
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor defaultBatchFetchSize(int defaultBatchFetchSize) {
		options.defaultBatchFetchSize( defaultBatchFetchSize );
		return this;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SessionBuilderImplementor subselectFetchEnabled(boolean subselectFetchEnabled) {
		options.subselectFetchEnabled( subselectFetchEnabled );
		return this;
	}
}
