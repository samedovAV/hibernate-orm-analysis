/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc.mutation.spi;

import org.hibernate.sql.model.jdbc.JdbcValueDescriptor;
import org.hibernate.type.descriptor.ValueBinder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Binding of a {@linkplain #getValue() value} for a {@link java.sql.PreparedStatement} parameter
 * by {@linkplain #getPosition() position}.
 *
 * @author Steve Ebersole
 */
public class Binding {
	private final String columnName;
	private Object value;
	private final JdbcValueDescriptor valueDescriptor;

	public Binding(String columnName, Object value, JdbcValueDescriptor valueDescriptor) {
		this.columnName = columnName;
		this.value = value;
		this.valueDescriptor = valueDescriptor;
	}

	/**
	 * The name of the column to which this value is "bound"
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getColumnName() {
		return columnName;
	}

	/**
	 * The value to be bound to the parameter
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getValue() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setValue(Object newValue) {
		this.value = newValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValueDescriptor getValueDescriptor() {
		return valueDescriptor;
	}

	/**
	 * The binder to be used in binding this value
	 */
	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T> ValueBinder<T> getValueBinder() {
		return getValueDescriptor().getJdbcMapping().getJdbcValueBinder();
	}

	/**
	 * The JDBC parameter position
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getPosition() {
		return getValueDescriptor().getJdbcPosition();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode() {
		return getPosition();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		final Binding other = (Binding) o;
		return getPosition() == other.getPosition();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "Binding(" + columnName + ")";
	}
}
