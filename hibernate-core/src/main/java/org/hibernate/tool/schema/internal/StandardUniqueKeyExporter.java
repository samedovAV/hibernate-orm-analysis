/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.mapping.UniqueKey;
import org.hibernate.tool.schema.spi.Exporter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An {@link Exporter} for {@linkplain UniqueKey unique constraints}.
 *
 * @author Brett Meyer
 *
 * @see org.hibernate.dialect.unique.UniqueDelegate
 */
public class StandardUniqueKeyExporter implements Exporter<UniqueKey> {
	private final Dialect dialect;

	public StandardUniqueKeyExporter(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlCreateStrings(UniqueKey constraint, Metadata metadata, SqlStringGenerationContext context) {
		return new String[] { dialect.getUniqueDelegate()
				.getAlterTableToAddUniqueKeyCommand( constraint, metadata, context ) };
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlDropStrings(UniqueKey constraint, Metadata metadata, SqlStringGenerationContext context) {
		return new String[] { dialect.getUniqueDelegate()
				.getAlterTableToDropUniqueKeyCommand( constraint, metadata, context ) };
	}
}
