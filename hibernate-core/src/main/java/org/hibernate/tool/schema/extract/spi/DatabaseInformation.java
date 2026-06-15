/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import org.hibernate.Incubating;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.QualifiedTableName;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Provides access to information about existing schema objects (tables, sequences etc) of existing database.
 *
 * @author Christoph Sturm
 * @author Teodor Danciu
 * @author Steve Ebersole
 */
@Incubating
public interface DatabaseInformation {
	/**
	 * Check to see if the given schema already exists.
	 *
	 * @param schema The schema name
	 *
	 * @return {@code true} indicates a schema with the given name already exists
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean schemaExists(Namespace.Name schema);

	/**
	 * Obtain reference to the named TableInformation
	 *
	 * @param catalogName The name of the catalog which contains the schema which the table belongs to
	 * @param schemaName The name of the schema the table belongs to
	 * @param tableName The table name
	 *
	 * @return The table information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableInformation getTableInformation(Identifier catalogName, Identifier schemaName, Identifier tableName);

	/**
	 * Obtain reference to the named TableInformation
	 *
	 * @param schemaName The name of the schema the table belongs to
	 * @param tableName The table name
	 *
	 * @return The table information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableInformation getTableInformation(Namespace.Name schemaName, Identifier tableName);

	/**
	 * Obtain reference to the named TableInformation
	 *
	 * @param tableName The qualified table name
	 *
	 * @return The table information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableInformation getTableInformation(QualifiedTableName tableName);

	/**
	 * Obtain reference to all the {@link TableInformation} for a given {@link Namespace}
	 *
	 * @param namespace The {@link Namespace} which contains the {@link TableInformation}
	 *
	 * @return a {@link NameSpaceTablesInformation}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NameSpaceTablesInformation getTablesInformation(Namespace namespace);

	/**
	 * Obtain reference to the named SequenceInformation
	 *
	 * @param catalogName The name of the catalog which contains the schema which the sequence belongs to
	 * @param schemaName The name of the schema the sequence belongs to
	 * @param sequenceName The sequence name
	 *
	 * @return The sequence information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SequenceInformation getSequenceInformation(
			Identifier catalogName,
			Identifier schemaName,
			Identifier sequenceName);

	/**
	 * Obtain reference to the named SequenceInformation
	 *
	 * @param schemaName The name of the schema the table belongs to
	 * @param sequenceName The sequence name
	 *
	 * @return The sequence information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SequenceInformation getSequenceInformation(Namespace.Name schemaName, Identifier sequenceName);

	/**
	 * Obtain reference to the named SequenceInformation
	 *
	 * @param sequenceName The qualified sequence name
	 *
	 * @return The sequence information.  May return {@code null} if not found.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SequenceInformation getSequenceInformation(QualifiedSequenceName sequenceName);

	/**
	 * Check to see if the given catalog already exists.
	 *
	 * @param catalog The catalog name
	 *
	 * @return {@code true} indicates a catalog with the given name already exists
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean catalogExists(Identifier catalog);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void cleanup();
}
