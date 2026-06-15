/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Basic contract for an insert operation
 *
 * @author Steve Ebersole
 */
public interface JdbcOperationQueryInsert extends JdbcOperationQueryMutation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getUniqueConstraintNameThatMayFail();
}
