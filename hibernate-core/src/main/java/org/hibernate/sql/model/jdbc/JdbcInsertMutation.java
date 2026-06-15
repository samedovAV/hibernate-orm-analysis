/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.jdbc;

import java.util.List;

import org.hibernate.jdbc.Expectation;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.MutationTarget;
import org.hibernate.sql.model.MutationType;
import org.hibernate.sql.model.TableMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for a table insert originating from a flush
 *
 * @author Steve Ebersole
 */
public class JdbcInsertMutation extends AbstractJdbcMutation {
	public JdbcInsertMutation(
			TableMapping tableDetails,
			MutationTarget<?,?> mutationTarget,
			String sql,
			boolean callable,
			Expectation expectation,
			List<? extends JdbcParameterBinder> parameterBinders) {
		super( tableDetails, mutationTarget, sql, callable, expectation, parameterBinders );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MutationType getMutationType() {
		return MutationType.INSERT;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "JdbcInsertMutation(" + getTableDetails().getTableName() + ")";
	}
}
