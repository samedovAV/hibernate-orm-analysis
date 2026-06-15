/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql;

import org.hibernate.query.sqm.spi.JdbcParameterBySqmParameterAccess;
import org.hibernate.sql.ast.spi.FromClauseAccess;
import org.hibernate.sql.ast.tree.Statement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SqmTranslator<T extends Statement>
		extends SqmToSqlAstConverter, FromClauseAccess, JdbcParameterBySqmParameterAccess {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmTranslation<T> translate();
}
