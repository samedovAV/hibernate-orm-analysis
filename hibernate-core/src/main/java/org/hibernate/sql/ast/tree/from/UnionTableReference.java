/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.Locale;
import java.util.function.Function;

import org.hibernate.spi.NavigablePath;

import static org.hibernate.internal.util.StringHelper.isEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class UnionTableReference extends NamedTableReference {
	private final String[] subclassTableSpaceExpressions;

	public UnionTableReference(
			String unionTableExpression,
			String[] subclassTableSpaceExpressions,
			String identificationVariable) {
		this( unionTableExpression, subclassTableSpaceExpressions, identificationVariable, false );
	}

	public UnionTableReference(
			String unionTableExpression,
			String[] subclassTableSpaceExpressions,
			String identificationVariable,
			boolean isOptional) {
		super( unionTableExpression, identificationVariable, isOptional );

		this.subclassTableSpaceExpressions = subclassTableSpaceExpressions;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference resolveTableReference(
			NavigablePath navigablePath,
			String tableExpression) {
		if ( hasTableExpression( tableExpression ) ) {
			return this;
		}

		throw new UnknownTableReferenceException(
				tableExpression,
				String.format(
						Locale.ROOT,
						"Unable to determine TableReference (`%s`) for `%s`",
						tableExpression,
						navigablePath
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve) {
		if ( hasTableExpression( tableExpression ) ) {
			return this;
		}
		return null;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private boolean hasTableExpression(String tableExpression) {
		for ( String expression : subclassTableSpaceExpressions ) {
			if ( tableExpression.equals( expression ) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean containsAffectedTableName(String requestedName) {
		return isEmpty( requestedName ) || hasTableExpression( requestedName );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Boolean visitAffectedTableNames(Function<String, Boolean> nameCollector) {
		for ( String expression : subclassTableSpaceExpressions ) {
			final Boolean result = nameCollector.apply( expression );
			if ( result != null ) {
				return result;
			}
		}
		return null;
	}
}
