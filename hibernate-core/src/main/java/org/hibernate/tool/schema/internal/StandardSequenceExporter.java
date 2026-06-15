/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.schema.spi.Exporter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An {@link Exporter} for {@linkplain Sequence sequences}.
 *
 * @author Steve Ebersole
 *
 * @see org.hibernate.dialect.sequence.SequenceSupport
 */
public class StandardSequenceExporter implements Exporter<Sequence> {
	private final Dialect dialect;

	public StandardSequenceExporter(Dialect dialect) {
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlCreateStrings(Sequence sequence, Metadata metadata, SqlStringGenerationContext context) {
		return dialect.getSequenceSupport().getCreateSequenceStrings(
				getFormattedSequenceName( sequence.getName(), metadata, context ),
				sequence.getInitialValue(),
				sequence.getIncrementSize(),
				sequence.getOptions()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] getSqlDropStrings(Sequence sequence, Metadata metadata, SqlStringGenerationContext context) {
		return dialect.getSequenceSupport().getDropSequenceStrings(
				getFormattedSequenceName( sequence.getName(), metadata, context )
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String getFormattedSequenceName(QualifiedSequenceName name, Metadata metadata,
			SqlStringGenerationContext context) {
		return context.format( name );
	}
}
