/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.sql.Statement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to a JDBC {@linkplain Statement}.
 *
 * @apiNote Intended for cases where sharing a common JDBC {@linkplain Statement} is useful, generally for performance.
 * @implNote Manages various tasks around creation and ensuring it gets cleaned up.
 *
 * @author Steve Ebersole
 */
public interface StatementAccess {
	/**
	 * Access the JDBC {@linkplain Statement}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Statement getJdbcStatement();
}
