/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import org.hibernate.tool.schema.extract.spi.ExtractionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @since 7.2
 */
public class InformationExtractorOracleImpl extends InformationExtractorJdbcDatabaseMetaDataImpl {

	public InformationExtractorOracleImpl(ExtractionContext extractionContext) {
		super( extractionContext );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsBulkPrimaryKeyRetrieval() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsBulkForeignKeyRetrieval() {
		return true;
	}

	// Unfortunately, there is no support for table wildcard for indexes
}
