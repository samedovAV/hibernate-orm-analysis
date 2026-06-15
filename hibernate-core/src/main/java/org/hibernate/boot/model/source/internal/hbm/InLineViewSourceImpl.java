/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.InLineViewSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class InLineViewSourceImpl
		extends AbstractHbmSourceNode
		implements InLineViewSource {
	private final String schemaName;
	private final String catalogName;
	private final String selectStatement;
	private final String logicalName;
	private final String comment;

	public InLineViewSourceImpl(
			MappingDocument mappingDocument,
			String schemaName,
			String catalogName,
			String selectStatement,
			String logicalName,
			String comment) {
		super( mappingDocument );
		this.schemaName = determineSchemaName( mappingDocument, schemaName );
		this.catalogName = determineCatalogName( mappingDocument, catalogName );
		this.selectStatement = selectStatement;
		this.logicalName = logicalName;
		this.comment = comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitSchemaName() {
		return schemaName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitCatalogName() {
		return catalogName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectStatement() {
		return selectStatement;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getLogicalName() {
		return logicalName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return comment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String determineCatalogName(MappingDocument mappingDocument, String catalogName) {
		return catalogName != null ? catalogName : mappingDocument.getDocumentRoot().getCatalog();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private String determineSchemaName(MappingDocument mappingDocument, String schemaName) {
		return schemaName != null ? schemaName : mappingDocument.getDocumentRoot().getSchema();
	}
}
