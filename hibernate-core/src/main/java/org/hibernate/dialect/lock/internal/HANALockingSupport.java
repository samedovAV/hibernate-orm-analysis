/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.lock.internal;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.RowLockStrategy;
import org.hibernate.dialect.lock.PessimisticLockStyle;
import org.hibernate.dialect.lock.spi.ConnectionLockTimeoutStrategy;
import org.hibernate.dialect.lock.spi.LockingSupport;
import org.hibernate.dialect.lock.spi.OuterJoinLockingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * LockingSupport for HANADialect
 *
 * @author Steve Ebersole
 */
public class HANALockingSupport extends LockingSupportParameterized {
	public static final HANALockingSupport HANA_LOCKING_SUPPORT = new HANALockingSupport( true, true	);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static LockingSupport forDialectVersion(DatabaseVersion version) {
		final boolean supportsWait = version.isSameOrAfter( 2, 0, 10 );
		final boolean supportsSkipLocked = version.isSameOrAfter(2, 0, 30);
		return new HANALockingSupport( supportsWait, supportsSkipLocked );
	}

	public HANALockingSupport(boolean supportsSkipLocked) {
		this( false, supportsSkipLocked );
	}

	private HANALockingSupport(boolean supportsWait, boolean supportsSkipLocked) {
		super(
				PessimisticLockStyle.CLAUSE,
				RowLockStrategy.COLUMN,
				supportsWait,
				supportsWait,
				supportsSkipLocked,
				OuterJoinLockingType.IDENTIFIED
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Metadata getMetadata() {
		return this;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RowLockStrategy getWriteRowLockStrategy() {
		return RowLockStrategy.COLUMN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public OuterJoinLockingType getOuterJoinLockingType() {
		return OuterJoinLockingType.IDENTIFIED;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ConnectionLockTimeoutStrategy getConnectionLockTimeoutStrategy() {
		return ConnectionLockTimeoutStrategy.NONE;
	}
}
