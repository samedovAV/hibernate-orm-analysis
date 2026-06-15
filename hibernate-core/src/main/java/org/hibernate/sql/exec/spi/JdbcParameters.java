/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The collection
 * @author Steve Ebersole
 */
public interface JdbcParameters {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addParameter(JdbcParameter parameter);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addParameters(Collection<JdbcParameter> parameters);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<JdbcParameter> getJdbcParameters();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default void visitJdbcParameters(Consumer<JdbcParameter> jdbcParameterAction) {
		for ( JdbcParameter jdbcParameter : getJdbcParameters() ) {
			jdbcParameterAction.accept( jdbcParameter );
		}
	}
}
