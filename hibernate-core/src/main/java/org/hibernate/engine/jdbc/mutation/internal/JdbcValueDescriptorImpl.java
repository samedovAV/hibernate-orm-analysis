/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.internal;

import org.hibernate.engine.jdbc.mutation.ParameterUsage;
import org.hibernate.metamodel.mapping.JdbcMapping;
import org.hibernate.sql.exec.spi.JdbcParameterBinder;
import org.hibernate.sql.model.ast.ColumnValueParameter;
import org.hibernate.sql.model.jdbc.JdbcValueDescriptor;

import static java.util.Locale.ROOT;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard {@link JdbcValueDescriptor} implementation
 *
 * @author Steve Ebersole
 */
public class JdbcValueDescriptorImpl implements JdbcValueDescriptor {
	private final String columnName;
	private final ParameterUsage usage;
	private final JdbcMapping jdbcMapping;
	private final int jdbcPosition;

	public JdbcValueDescriptorImpl(JdbcParameterBinder jdbcParameterBinder, int jdbcPosition) {
		this( (ColumnValueParameter) jdbcParameterBinder, jdbcPosition );
	}

	public JdbcValueDescriptorImpl(ColumnValueParameter columnValueParameter, int jdbcPosition) {
		this.columnName = columnValueParameter.getColumnReference().getColumnExpression();
		this.usage = columnValueParameter.getUsage();
		this.jdbcMapping = columnValueParameter.getJdbcMapping();
		this.jdbcPosition = jdbcPosition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getColumnName() {
		return columnName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ParameterUsage getUsage() {
		return usage;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getJdbcPosition() {
		return jdbcPosition;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcMapping getJdbcMapping() {
		return jdbcMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return String.format( ROOT, "JdbcValueDescriptor(%s, %s, %d)", columnName, usage, jdbcPosition );
	}
}
