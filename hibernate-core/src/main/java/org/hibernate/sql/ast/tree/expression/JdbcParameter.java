/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.expression;

import jakarta.annotation.Nullable;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JdbcParameter extends Expression {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcParameterBinder getParameterBinder();
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getParameterId();
}
