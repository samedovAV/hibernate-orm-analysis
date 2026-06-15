/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal;

import org.hibernate.StatementObserver;

import java.io.Serializable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Default StatementObserver implementation.  Simply ignores call.
///
/// @author Steve Ebersole
public class IgnoredStatementObserver implements StatementObserver, Serializable {
	public static final IgnoredStatementObserver IGNORE = new IgnoredStatementObserver();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void performingSql(String sql, int batchPosition) {
		// just ignore
	}
}
