/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import jakarta.annotation.Nullable;
import org.hibernate.query.sqm.mutation.spi.AfterUseAction;
import org.hibernate.query.sqm.mutation.spi.BeforeUseAction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines how to interact with a certain temporary table kind.
 *
 * @since 7.1
 */
public interface TemporaryTableStrategy {

	/**
	 * Returns an adjusted table name that can be used for temporary tables.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String adjustTemporaryTableName(String desiredTableName);

	/**
	 * The kind of temporary tables that are supported on this database.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TemporaryTableKind getTemporaryTableKind();

	/**
	 * An arbitrary SQL fragment appended to the end of the statement to
	 * create a temporary table, specifying dialect-specific options, or
	 * {@code null} if there are no options to specify.
	 */
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTemporaryTableCreateOptions();

	/**
	 * The command to create a temporary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTemporaryTableCreateCommand();

	/**
	 * The command to drop a temporary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTemporaryTableDropCommand();

	/**
	 * The command to truncate a temporary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTemporaryTableTruncateCommand();

	/**
	 * Annotation to be appended to the end of each COLUMN clause for temporary tables.
	 *
	 * @param sqlTypeCode The SQL type code
	 * @return The annotation to be appended, for example, {@code COLLATE DATABASE_DEFAULT} in SQL Server
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCreateTemporaryTableColumnAnnotation(int sqlTypeCode);

	/**
	 * The action to take after finishing use of a temporary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AfterUseAction getTemporaryTableAfterUseAction();

	/**
	 * The action to take before beginning use of a temporary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BeforeUseAction getTemporaryTableBeforeUseAction();

	/**
	 * Does this database support primary keys for temporary tables for this strategy?
	 *
	 * @return true by default, since most do
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean supportsTemporaryTablePrimaryKey() {
		return true;
	}

	/**
	 * Does this database support null constraints for temporary table columns for this strategy?
	 *
	 * @return true by default, since most do
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean supportsTemporaryTableNullConstraint() {
		return true;
	}
}
