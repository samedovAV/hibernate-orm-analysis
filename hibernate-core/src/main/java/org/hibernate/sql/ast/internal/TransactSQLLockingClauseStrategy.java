/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.internal;

import jakarta.persistence.PessimisticLockScope;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.spi.SqlAppender;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// LockingClauseStrategy implementation for T-SQL (SQL Server and Sybase)
///
/// @author Steve Ebersole
public class TransactSQLLockingClauseStrategy extends AbstractLockingClauseStrategy {

	public TransactSQLLockingClauseStrategy(PessimisticLockScope lockingScope, Set<NavigablePath> rootsForLocking) {
		super( lockingScope, rootsForLocking );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsOuterJoins() {
		// not used for T-SQL dialects
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsJoins() {
		// not used for T-SQL dialects
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void render(SqlAppender sqlAppender) {
		// not used for T-SQL dialects
	}
}
