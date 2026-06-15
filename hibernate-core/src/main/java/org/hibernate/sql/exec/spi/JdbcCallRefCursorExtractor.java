/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JdbcCallRefCursorExtractor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ResultSet extractResultSet(CallableStatement callableStatement, SharedSessionContractImplementor session);
}
