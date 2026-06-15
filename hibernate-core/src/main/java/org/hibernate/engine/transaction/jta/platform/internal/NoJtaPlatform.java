/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import jakarta.transaction.Status;
import jakarta.transaction.Synchronization;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;

import jakarta.annotation.Nullable;

import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The non-configured form of JTA platform.  This is what is used if none was set up.
 *
 * @author Steve Ebersole
 */
public class NoJtaPlatform implements JtaPlatform {
	public static final NoJtaPlatform INSTANCE = new NoJtaPlatform();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable TransactionManager retrieveTransactionManager() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable UserTransaction retrieveUserTransaction() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public @Nullable Object getTransactionIdentifier(Transaction transaction) {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerSynchronization(Synchronization synchronization) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean canRegisterSynchronization() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getCurrentStatus() throws SystemException {
		return Status.STATUS_UNKNOWN;
	}
}
