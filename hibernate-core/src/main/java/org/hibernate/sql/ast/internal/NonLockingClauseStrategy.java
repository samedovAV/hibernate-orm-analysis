/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.internal;

import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.LockingClauseStrategy;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.ast.tree.from.TableGroupJoin;

import java.util.Collection;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * LockingClauseStrategy implementation for cases when a dialect
 * applies locking in the {@code FROM clause} (e.g., SQL Server).
 * It is also used for cases where no locking was requested.
 *
 * @author Steve Ebersole
 */
public class NonLockingClauseStrategy implements LockingClauseStrategy {
	public static final NonLockingClauseStrategy NON_CLAUSE_STRATEGY = new NonLockingClauseStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean registerRoot(TableGroup root) {
		// nothing to do
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean registerJoin(TableGroupJoin join) {
		// nothing to do
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsOuterJoins() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsJoins() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void render(SqlAppender sqlAppender) {
		// nothing to do
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Collection<NavigablePath> getPathsToLock() {
		return List.of();
	}
}
