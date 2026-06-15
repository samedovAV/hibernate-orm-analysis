/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import org.hibernate.Incubating;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.resource.transaction.spi.DdlTransactionIsolator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import org.hibernate.tool.schema.extract.spi.InformationExtractor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Encapsulates the functionality for extracting database metadata used by
 * {@link SchemaManagementTool}.
 *
 * @author Gail Badner
 */
@Incubating
public interface ExtractionTool {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ExtractionContext createExtractionContext(
			ServiceRegistry serviceRegistry,
			JdbcEnvironment jdbcEnvironment,
			SqlStringGenerationContext context,
			DdlTransactionIsolator ddlTransactionIsolator,
			ExtractionContext.DatabaseObjectAccess databaseObjectAccess);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InformationExtractor createInformationExtractor(ExtractionContext extractionContext);
}
