/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.List;
import java.util.function.Function;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.insert.Values;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class ValuesTableReference extends DerivedTableReference {

	private final List<Values> valuesList;

	public ValuesTableReference(
			List<Values> valuesList,
			String identificationVariable,
			List<String> columnNames,
			SessionFactoryImplementor sessionFactory) {
		super( identificationVariable, columnNames, false, sessionFactory );
		this.valuesList = valuesList;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableId() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<Values> getValuesList() {
		return valuesList;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		sqlTreeWalker.visitValuesTableReference( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean visitAffectedTableNames(Function<String, Boolean> nameCollector) {
		return null;
	}

}
