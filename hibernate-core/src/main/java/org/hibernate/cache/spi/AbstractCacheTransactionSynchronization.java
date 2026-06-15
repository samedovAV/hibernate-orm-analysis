/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public abstract class AbstractCacheTransactionSynchronization implements CacheTransactionSynchronization {
	private long lastTransactionCompletionTimestamp;
	private final RegionFactory regionFactory;

	public AbstractCacheTransactionSynchronization(RegionFactory regionFactory) {
		// prime the timestamp for any non-transactional access - until (if) we
		// 		later join a new txn
		this.lastTransactionCompletionTimestamp = regionFactory.nextTimestamp();
		this.regionFactory = regionFactory;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getCachingTimestamp() {
		return lastTransactionCompletionTimestamp;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final void transactionJoined() {
		// reset the timestamp
		this.lastTransactionCompletionTimestamp = regionFactory.nextTimestamp();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final void transactionCompleting() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void transactionCompleted(boolean successful) {
		// reset the timestamp for any non-transactional access after this
		// 		point - until (if) we later join a new txn
//		this.lastTransactionCompletionTimestamp = regionFactory.nextTimestamp();
	}

}
