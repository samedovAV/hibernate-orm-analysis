/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import java.util.Map;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Synchronization;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;

import org.hibernate.engine.jndi.spi.JndiService;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformException;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import static org.hibernate.cfg.TransactionSettings.JTA_CACHE_TM;
import static org.hibernate.cfg.TransactionSettings.JTA_CACHE_UT;
import static org.hibernate.engine.transaction.internal.jta.JtaStatusHelper.isActive;
import static org.hibernate.internal.util.config.ConfigurationHelper.getBoolean;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractJtaPlatform
		implements JtaPlatform, Configurable, ServiceRegistryAwareService, TransactionManagerAccess {
	private boolean cacheTransactionManager;
	private boolean cacheUserTransaction;
	private ServiceRegistryImplementor serviceRegistry;

	private final JtaSynchronizationStrategy tmSynchronizationStrategy = new TransactionManagerBasedSynchronizationStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void injectServices(@Nonnull ServiceRegistryImplementor serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	private final class TransactionManagerBasedSynchronizationStrategy implements JtaSynchronizationStrategy {

		@Override
		@Prove(complexity = Complexity.O_N, n = "", count = {})
		public void registerSynchronization(Synchronization synchronization) {
			try {
				getTransactionManager().getTransaction().registerSynchronization( synchronization );
			}
			catch (Exception e) {
				throw new JtaPlatformException( "Could not access JTA Transaction to register synchronization", e );
			}
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public boolean canRegisterSynchronization() {
			return isActive( getTransactionManager() );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected ServiceRegistry serviceRegistry() {
		return serviceRegistry;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JndiService jndiService() {
		return serviceRegistry().requireService( JndiService.class );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract TransactionManager locateTransactionManager();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract UserTransaction locateUserTransaction();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void configure(@Nonnull Map<String, Object> configValues) {
		cacheTransactionManager = getBoolean( JTA_CACHE_TM, configValues, canCacheTransactionManagerByDefault() );
		cacheUserTransaction = getBoolean( JTA_CACHE_UT, configValues, canCacheUserTransactionByDefault() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean canCacheTransactionManagerByDefault() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean canCacheUserTransactionByDefault() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean canCacheTransactionManager() {
		return cacheTransactionManager;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean canCacheUserTransaction() {
		return cacheUserTransaction;
	}

	private @Nullable TransactionManager transactionManager;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionManager retrieveTransactionManager() {
		if ( canCacheTransactionManager() ) {
			if ( transactionManager == null ) {
				transactionManager = locateTransactionManager();
			}
			return transactionManager;
		}
		else {
			return locateTransactionManager();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionManager getTransactionManager() {
		return retrieveTransactionManager();
	}

	private @Nullable UserTransaction userTransaction;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public UserTransaction retrieveUserTransaction() {
		if ( canCacheUserTransaction() ) {
			if ( userTransaction == null ) {
				userTransaction = locateUserTransaction();
			}
			return userTransaction;
		}
		else {
			return locateUserTransaction();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getTransactionIdentifier(Transaction transaction) {
		// generally we use the transaction itself.
		return transaction;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected JtaSynchronizationStrategy getSynchronizationStrategy() {
		return tmSynchronizationStrategy;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerSynchronization(Synchronization synchronization) {
		getSynchronizationStrategy().registerSynchronization( synchronization );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean canRegisterSynchronization() {
		return getSynchronizationStrategy().canRegisterSynchronization();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getCurrentStatus() throws SystemException {
		return retrieveTransactionManager().getStatus();
	}
}
