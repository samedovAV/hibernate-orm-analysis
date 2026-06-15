/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.internal;

import org.hibernate.cache.spi.CacheTransactionSynchronization;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NoCachingTransactionSynchronizationImpl implements CacheTransactionSynchronization {
	public static final NoCachingTransactionSynchronizationImpl INSTANCE = new NoCachingTransactionSynchronizationImpl();

	private NoCachingTransactionSynchronizationImpl() {
		//noop
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public long getCachingTimestamp() {
		throw new UnsupportedOperationException("Method not supported when 2LC is not enabled");
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void transactionJoined() {
		//noop
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void transactionCompleting() {
		//noop
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void transactionCompleted(boolean successful) {
		//noop
	}
}
