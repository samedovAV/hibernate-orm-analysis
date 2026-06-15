/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.Objects;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public abstract class AbstractTableReference implements TableReference {
	protected final String identificationVariable;
	protected final boolean isOptional;

	public AbstractTableReference(String identificationVariable, boolean isOptional) {
		assert identificationVariable != null;
		this.identificationVariable = identificationVariable;
		this.isOptional = isOptional;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentificationVariable() {
		return identificationVariable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOptional() {
		return isOptional;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		TableReference that = (TableReference) o;
		return Objects.equals( identificationVariable, that.getIdentificationVariable() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode() {
		return Objects.hash( identificationVariable );
	}
}
