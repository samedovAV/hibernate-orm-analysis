/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.SqlAstWalker;
import org.hibernate.sql.ast.tree.SqlAstNode;

import jakarta.annotation.Nullable;

import static org.hibernate.internal.util.StringHelper.isEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a reference to a table (derived or physical) in a query's from clause.
 *
 * @author Steve Ebersole
 */
public interface TableReference extends SqlAstNode, ColumnReferenceQualifier {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIdentificationVariable();

	/**
	 * An identifier for the table reference. May be null if this is not a named table reference.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOptional();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(SqlAstWalker sqlTreeWalker);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void applyAffectedTableNames(Consumer<String> nameCollector) {
		visitAffectedTableNames(
				name -> {
					nameCollector.accept( name );
					return null;
				}
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<String> getAffectedTableNames() {
		final List<String> affectedTableNames = new ArrayList<>();
		visitAffectedTableNames(
				name -> {
					affectedTableNames.add( name );
					return null;
				}
		);
		return affectedTableNames;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean containsAffectedTableName(String requestedName) {
		return isEmpty( requestedName ) || Boolean.TRUE.equals( visitAffectedTableNames( requestedName::equals ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean visitAffectedTableNames(Function<String, Boolean> nameCollector);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableReference resolveTableReference(
			NavigablePath navigablePath,
			String tableExpression);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableReference getTableReference(
			NavigablePath navigablePath,
			String tableExpression,
			boolean resolve);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isEmbeddableFunctionTableReference() {
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable EmbeddableFunctionTableReference asEmbeddableFunctionTableReference() {
		return null;
	}
}
