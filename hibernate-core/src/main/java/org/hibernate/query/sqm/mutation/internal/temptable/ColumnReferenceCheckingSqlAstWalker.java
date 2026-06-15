/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.mutation.internal.temptable;

import org.hibernate.persister.internal.SqlFragmentPredicate;
import org.hibernate.sql.ast.spi.AbstractSqlAstWalker;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.predicate.FilterPredicate;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Visitor to determine if all visited column references use the same qualifier.
 */
public class ColumnReferenceCheckingSqlAstWalker extends AbstractSqlAstWalker {

	private final String identificationVariable;
	private boolean allColumnReferencesFromIdentificationVariable = true;

	public ColumnReferenceCheckingSqlAstWalker(String identificationVariable) {
		this.identificationVariable = identificationVariable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isAllColumnReferencesFromIdentificationVariable() {
		return allColumnReferencesFromIdentificationVariable;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitSelectStatement(SelectStatement statement) {
		// Ignore subquery
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitColumnReference(ColumnReference columnReference) {
		if ( allColumnReferencesFromIdentificationVariable && !identificationVariable.equals( columnReference.getQualifier() ) ) {
			allColumnReferencesFromIdentificationVariable = false;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitFilterPredicate(FilterPredicate filterPredicate) {
		allColumnReferencesFromIdentificationVariable = false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitFilterFragmentPredicate(FilterPredicate.FilterFragmentPredicate fragmentPredicate) {
		allColumnReferencesFromIdentificationVariable = false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void visitSqlFragmentPredicate(SqlFragmentPredicate predicate) {
		allColumnReferencesFromIdentificationVariable = false;
	}
}
