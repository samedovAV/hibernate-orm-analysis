/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.creation.internal.options;

import org.hibernate.Transaction;
import org.hibernate.engine.creation.internal.ParentSessionObserver;
import org.hibernate.engine.creation.internal.SharedSessionCreationOptions;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.TransactionCompletionCallbacksImplementor;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Common contract for options used when creating a session that may share
/// transaction/JDBC context with an existing session.
///
/// The shared state is derived from [#getOriginalSession()] only when
/// [#isTransactionCoordinatorShared()] is enabled. Otherwise, the shared
/// transaction-related accessors return `null`, causing normal independent
/// session construction.
///
/// @since 8.0
/// @author Steve Ebersole
public interface CommonSharedOptions extends SharedSessionCreationOptions {
	/// The session from which shared transaction/JDBC context may be inherited.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedSessionContractImplementor getOriginalSession();

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void registerParentSessionObserver(ParentSessionObserver observer) {
		registerParentSessionObserver( observer, getOriginalSession() );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default TransactionCoordinator getTransactionCoordinator() {
		return isTransactionCoordinatorShared()
				? getOriginalSession().getTransactionCoordinator()
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JdbcCoordinator getJdbcCoordinator() {
		return isTransactionCoordinatorShared()
				? getOriginalSession().getJdbcCoordinator()
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Transaction getTransaction() {
		return isTransactionCoordinatorShared()
				? getOriginalSession().getCurrentTransaction()
				: null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default TransactionCompletionCallbacksImplementor getTransactionCompletionCallbacks() {
		return isTransactionCoordinatorShared()
				? getOriginalSession().getTransactionCompletionCallbacksImplementor()
				: null;
	}
}
