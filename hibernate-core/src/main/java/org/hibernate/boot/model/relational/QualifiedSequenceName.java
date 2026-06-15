/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.relational;

import org.hibernate.boot.model.naming.Identifier;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class QualifiedSequenceName extends QualifiedNameImpl {
	public QualifiedSequenceName(Identifier catalogName, Identifier schemaName, Identifier sequenceName) {
		super( catalogName, schemaName, sequenceName );
	}

	public QualifiedSequenceName(Namespace.Name schemaName, Identifier sequenceName) {
		super( schemaName, sequenceName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier getSequenceName() {
		return getObjectName();
	}
}
