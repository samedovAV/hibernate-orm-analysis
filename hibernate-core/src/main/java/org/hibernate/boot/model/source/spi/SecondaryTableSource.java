/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;

import org.hibernate.boot.model.CustomSql;
import org.hibernate.engine.FetchStyle;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface SecondaryTableSource extends ForeignKeyContributingSource {
	/**
	 * Obtain the table being joined to.
	 *
	 * @return The joined table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableSpecificationSource getTableSource();

	/**
	 * Retrieves the columns defines as making up this secondary tables primary key.  Each entry should have
	 * a corresponding entry in the foreign-key columns described by the {@link ForeignKeyContributingSource}
	 * aspect of this contract.
	 *
	 * @return The columns defining the primary key for this secondary table
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ColumnSource> getPrimaryKeyColumnSources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getLogicalTableNameForContainedColumns();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchStyle getFetchStyle();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInverse();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOptional();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCascadeDeleteEnabled();

	/**
	 * Obtain the custom SQL to be used for inserts for this entity
	 *
	 * @return The custom insert SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlInsert();

	/**
	 * Obtain the custom SQL to be used for updates for this entity
	 *
	 * @return The custom update SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlUpdate();

	/**
	 * Obtain the custom SQL to be used for deletes for this entity
	 *
	 * @return The custom delete SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlDelete();
}
