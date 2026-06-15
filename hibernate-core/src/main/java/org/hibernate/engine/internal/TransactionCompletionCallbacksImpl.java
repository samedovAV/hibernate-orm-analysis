/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.internal;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacksImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class TransactionCompletionCallbacksImpl implements TransactionCompletionCallbacksImplementor {
	private final SharedSessionContractImplementor session;

	private BeforeTransactionCompletionProcessQueue beforeTransactionProcesses;
	private AfterTransactionCompletionProcessQueue afterTransactionProcesses;

	public TransactionCompletionCallbacksImpl(SharedSessionContractImplementor session) {
		this.session = session;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerCallback(BeforeCompletionCallback process) {
		if ( beforeTransactionProcesses == null ) {
			beforeTransactionProcesses = new BeforeTransactionCompletionProcessQueue( session );
		}
		beforeTransactionProcesses.register( process );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasBeforeCompletionCallbacks() {
		return beforeTransactionProcesses != null
			&& beforeTransactionProcesses.hasActions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void beforeTransactionCompletion() {
		if ( beforeTransactionProcesses != null && beforeTransactionProcesses.hasActions() ) {
			beforeTransactionProcesses.beforeTransactionCompletion();
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerCallback(AfterCompletionCallback process) {
		if ( afterTransactionProcesses == null ) {
			afterTransactionProcesses = new AfterTransactionCompletionProcessQueue( session );
		}
		afterTransactionProcesses.register( process );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasAfterCompletionCallbacks() {
		return afterTransactionProcesses != null && afterTransactionProcesses.hasActions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterTransactionCompletion(boolean success) {
		if ( afterTransactionProcesses != null && afterTransactionProcesses.hasActions() ) {
			afterTransactionProcesses.afterTransactionCompletion( success );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addSpaceToInvalidate(String space) {
		if ( afterTransactionProcesses == null ) {
			afterTransactionProcesses = new AfterTransactionCompletionProcessQueue( session );
		}
		afterTransactionProcesses.addSpaceToInvalidate( space );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TransactionCompletionCallbacksImpl forSharing() {
		if ( beforeTransactionProcesses == null ) {
			beforeTransactionProcesses = new BeforeTransactionCompletionProcessQueue( session );
		}
		if ( afterTransactionProcesses == null ) {
			afterTransactionProcesses = new AfterTransactionCompletionProcessQueue( session );
		}
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void executePendingBulkOperationCleanUpActions() {
		if ( afterTransactionProcesses != null ) {
			afterTransactionProcesses.executePendingBulkOperationCleanUpActions();
		}
	}
}
