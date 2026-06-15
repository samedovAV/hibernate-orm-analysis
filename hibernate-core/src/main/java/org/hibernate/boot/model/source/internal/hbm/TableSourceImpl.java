/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.TableSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models a table mapping source.
 *
 * @author Steve Ebersole
 */
public class TableSourceImpl extends AbstractHbmSourceNode implements TableSource {
	private final String explicitCatalog;
	private final String explicitSchema;
	private final String explicitTableName;
	private final String rowId;
	private final String comment;
	private final String checkConstraint;

	TableSourceImpl(
			MappingDocument mappingDocument,
			String explicitSchema,
			String explicitCatalog,
			String explicitTableName,
			String rowId,
			String comment,
			String checkConstraint) {
		super( mappingDocument );
		this.explicitCatalog = determineCatalogName( mappingDocument, explicitCatalog );
		this.explicitSchema = determineSchemaName( mappingDocument, explicitSchema );
		this.explicitTableName = explicitTableName;
		this.rowId = rowId;
		this.comment = comment;
		this.checkConstraint = checkConstraint;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitCatalogName() {
		return explicitCatalog;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitSchemaName() {
		return explicitSchema;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitTableName() {
		return explicitTableName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getRowId() {
		return rowId;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCheckConstraint() {
		return checkConstraint;
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
