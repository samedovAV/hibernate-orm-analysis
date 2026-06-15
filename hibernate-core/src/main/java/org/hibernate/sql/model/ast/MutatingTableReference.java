/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model.ast;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import org.hibernate.metamodel.mapping.ValuedModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.from.TableReference;
import org.hibernate.sql.model.TableMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialized TableReference for model mutation operations
 *
 * @author Steve Ebersole
 */
public class MutatingTableReference implements TableReference {
	private final TableMapping tableMapping;

	public MutatingTableReference(TableMapping tableMapping) {
		this.tableMapping = tableMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableMapping getTableMapping() {
		return tableMapping;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getTableName() {
		return tableMapping.getTableName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentificationVariable() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableId() {
		return getTableName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isOptional() {
		return tableMapping.isOptional();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(SqlAstWalker sqlTreeWalker) {
		throw new UnsupportedOperationException( "Mutating table reference should be handled by the statement visitation" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean visitAffectedTableNames(Function<String, Boolean> nameCollector) {
		return nameCollector.apply( getTableName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference resolveTableReference(
			NavigablePath navigablePath,
			String tableExpression) {
		if ( getTableName().equals( tableExpression ) ) {
			return this;
		}

		throw new IllegalArgumentException(
				String.format(
						Locale.ROOT,
						"Table-expression (%s) did not match mutating table name - %s",
						tableExpression,
						getTableName()
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference resolveTableReference(
			NavigablePath navigablePath,
			ValuedModelPart modelPart,
			String tableExpression) {
		if ( getTableName().equals( tableExpression ) ) {
			return this;
		}

		throw new IllegalArgumentException(
				String.format(
						Locale.ROOT,
						"Table-expression (%s) did not match mutating table name - %s",
						tableExpression,
						getTableName()
				)
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getTableReference(NavigablePath navigablePath, String tableExpression, boolean resolve) {
		return getTableName().equals( tableExpression ) ? this : null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableReference getTableReference(
			NavigablePath navigablePath,
			ValuedModelPart modelPart,
			String tableExpression,
			boolean resolve) {
		return getTableName().equals( tableExpression ) ? this : null;
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
		final MutatingTableReference that = (MutatingTableReference) o;
		return Objects.equals( getTableName(), that.getTableName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int hashCode() {
		return Objects.hash( getTableName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "MutatingTableReference(" + getTableName() + ")";
	}
}
