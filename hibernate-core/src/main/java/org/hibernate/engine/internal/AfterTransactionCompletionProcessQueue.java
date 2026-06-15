/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.internal;

import org.hibernate.HibernateException;
import org.hibernate.action.internal.BulkOperationCleanupAction.BulkOperationCleanUpAfterTransactionCompletionProcess;
import org.hibernate.cache.CacheException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacks.AfterCompletionCallback;

import java.util.HashSet;
import java.util.Set;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import static org.hibernate.internal.util.collections.ArrayHelper.EMPTY_STRING_ARRAY;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Encapsulates behavior needed for after transaction processing
 */
class AfterTransactionCompletionProcessQueue
		extends AbstractTransactionCompletionProcessQueue<AfterCompletionCallback> {

	private final Set<String> querySpacesToInvalidate = new HashSet<>();

	AfterTransactionCompletionProcessQueue(SharedSessionContractImplementor session) {
		super( session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addSpaceToInvalidate(String space) {
		querySpacesToInvalidate.add( space );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	boolean hasActions() {
		return super.hasActions() || !querySpacesToInvalidate.isEmpty();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	void afterTransactionCompletion(boolean success) {
		AfterCompletionCallback process;
		while ( (process = processes.poll()) != null ) {
			callAfterCompletion( success, process );
		}
		invalidateCaches();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void executePendingBulkOperationCleanUpActions() {
		if ( performBulkOperationCallbacks() ) {
			invalidateCaches();
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean performBulkOperationCallbacks() {
		boolean hasPendingBulkOperationCleanUpActions = false;
		var iterator = processes.iterator();
		while ( iterator.hasNext() ) {
			var process = iterator.next();
			if ( process instanceof BulkOperationCleanUpAfterTransactionCompletionProcess ) {
				hasPendingBulkOperationCleanUpActions = true;
				if ( callAfterCompletion( true, process ) ) {
					iterator.remove();
				}
			}
		}
		return hasPendingBulkOperationCleanUpActions;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private boolean callAfterCompletion(boolean success, AfterCompletionCallback process) {
		try {
			process.doAfterTransactionCompletion( success, session );
			return true;
		}
		catch (CacheException ce) {
			CORE_LOGGER.unableToReleaseCacheLock( ce );
			// continue loop
			return false;
		}
		catch (Exception e) {
			throw new HibernateException(
					"Unable to perform afterTransactionCompletion callback: " + e.getMessage(), e );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private void invalidateCaches() {
		final var factory = session.getFactory();
		if ( factory.getSessionFactoryOptions().isQueryCacheEnabled() ) {
			factory.getCache().getTimestampsCache().
					invalidate( querySpacesToInvalidate.toArray( EMPTY_STRING_ARRAY ), session );
		}
		querySpacesToInvalidate.clear();
	}
}
