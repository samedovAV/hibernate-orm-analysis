/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.lock.internal;

import jakarta.persistence.Timeout;
import org.hibernate.dialect.lock.spi.ConnectionLockTimeoutStrategy;
import org.hibernate.dialect.lock.spi.LockTimeoutType;
import org.hibernate.dialect.lock.spi.LockingSupport;
import org.hibernate.dialect.lock.spi.OuterJoinLockingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * LockingSupport for HSQLDialect
 *
 * @author Steve Ebersole
 */
public class HSQLLockingSupport implements LockingSupport, LockingSupport.Metadata {
	public static final HSQLLockingSupport LOCKING_SUPPORT = new HSQLLockingSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Metadata getMetadata() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockTimeoutType getLockTimeoutType(Timeout timeout) {
		return LockTimeoutType.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OuterJoinLockingType getOuterJoinLockingType() {
		return OuterJoinLockingType.IGNORED;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ConnectionLockTimeoutStrategy getConnectionLockTimeoutStrategy() {
		return ConnectionLockTimeoutStrategy.NONE;
	}
}
