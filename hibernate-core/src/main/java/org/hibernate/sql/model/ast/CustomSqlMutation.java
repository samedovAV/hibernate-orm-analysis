/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import org.hibernate.sql.model.jdbc.JdbcMutationOperation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CustomSqlMutation<O extends JdbcMutationOperation> extends TableMutation<O> {
	/**
	 * The custom SQL provided by the mapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomSql();

	/**
	 * Whether {@link #getCustomSql()} represents a callable (function/procedure)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCallable();
}
