/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.spi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.select.QuerySpec;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A walker that detects nested correlation where a deeper subquery references
 * an intermediate subquery's table alias.
 *
 * @author Yoobin Yoon
 */
public class NestedCorrelationChecker extends AbstractSqlAstWalker {

	private final Set<String> currentAliases = new HashSet<>();
	protected List<String> currentLevelAliases;

	protected static class NestedCorrelationException extends RuntimeException {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Throwable fillInStackTrace() {
			return this;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static boolean hasNestedCorrelation(SqlAstNode node) {
		try {
			node.accept( new NestedCorrelationChecker() );
			return false;
		}
		catch (NestedCorrelationException ex) {
			return true;
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitQuerySpec(QuerySpec querySpec) {
		if ( currentLevelAliases != null ) {
			currentAliases.addAll( currentLevelAliases );
		}
		List<String> aliases = new ArrayList<>();
		if ( querySpec.getFromClause() != null ) {
			querySpec.getFromClause().visitTableReferences( tableReference -> {
				String alias = tableReference.getIdentificationVariable();
				if ( alias != null && !currentAliases.contains( alias ) ) {
					aliases.add( alias );
				}
			} );
		}

		List<String> previousLevelAliases = currentLevelAliases;
		currentLevelAliases = aliases;
		super.visitQuerySpec( querySpec );
		currentLevelAliases = previousLevelAliases;
		if ( previousLevelAliases != null ) {
			currentAliases.removeAll( previousLevelAliases );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void visitColumnReference(ColumnReference columnReference) {
		String qualifier = columnReference.getQualifier();
		if ( qualifier != null && currentAliases.contains( qualifier ) ) {
			throw new NestedCorrelationException();
		}
		super.visitColumnReference( columnReference );
	}
}
