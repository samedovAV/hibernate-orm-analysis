/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.lock.internal;

import jakarta.persistence.Timeout;
import org.hibernate.dialect.lock.PessimisticLockStyle;
import org.hibernate.dialect.lock.spi.ConnectionLockTimeoutStrategy;
import org.hibernate.dialect.lock.spi.LockTimeoutType;
import org.hibernate.dialect.lock.spi.LockingSupport;
import org.hibernate.dialect.lock.spi.OuterJoinLockingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class NoLockingSupport implements LockingSupport, LockingSupport.Metadata {
	public static final NoLockingSupport NO_LOCKING_SUPPORT = new NoLockingSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Metadata getMetadata() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ConnectionLockTimeoutStrategy getConnectionLockTimeoutStrategy() {
		return ConnectionLockTimeoutStrategy.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PessimisticLockStyle getPessimisticLockStyle() {
		return PessimisticLockStyle.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockTimeoutType getLockTimeoutType(Timeout timeout) {
		return LockTimeoutType.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OuterJoinLockingType getOuterJoinLockingType() {
		return OuterJoinLockingType.UNSUPPORTED;
	}
}
