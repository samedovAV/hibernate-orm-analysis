/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.spi;

import org.hibernate.query.sqm.tree.SqmStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Provides access to an underlying SQM AST
///
/// @author Steve Ebersole
public interface SqmStatementAccess<R> {
	/// Return the SQM AST.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmStatement<R> getSqmStatement();
}
