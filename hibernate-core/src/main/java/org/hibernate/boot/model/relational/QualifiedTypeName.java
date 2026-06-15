/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.relational;

import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class QualifiedTypeName extends QualifiedNameImpl {
	public QualifiedTypeName(Identifier catalogName, Identifier schemaName, Identifier tableName) {
		super( catalogName, schemaName, tableName );
	}

	public QualifiedTypeName(Namespace.Name schemaName, Identifier tableName) {
		super( schemaName, tableName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getTypeName() {
		return getObjectName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QualifiedTypeName quote() {
		Identifier catalogName = getCatalogName();
		if ( catalogName != null ) {
			catalogName = new Identifier( catalogName.getText(), true );
		}
		Identifier schemaName = getSchemaName();
		if ( schemaName != null ) {
			schemaName = new Identifier( schemaName.getText(), true );
		}
		Identifier tableName = getTypeName();
		if ( tableName != null ) {
			tableName = new Identifier( tableName.getText(), true );
		}
		return new QualifiedTypeName( catalogName, schemaName, tableName );
	}
}
