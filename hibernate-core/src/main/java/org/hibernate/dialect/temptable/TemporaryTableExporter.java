/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.temptable;

import java.util.function.Function;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An exporter for temporary tables.
 * <p>
 * Unlike other {@linkplain org.hibernate.tool.schema.spi.Exporter DDL exporters},
 * this exporter is called at runtime, instead of during schema management.
 *
 * @author Steve Ebersole
 */
public interface TemporaryTableExporter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlCreateCommand(TemporaryTable idTable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlDropCommand(TemporaryTable idTable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlTruncateCommand(
			TemporaryTable idTable,
			Function<SharedSessionContractImplementor, String> sessionUidAccess,
			SharedSessionContractImplementor session);
}
