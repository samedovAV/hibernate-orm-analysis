/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import org.hibernate.dialect.SpannerPostgreSQLDialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SpannerPostgreSQLSequenceSupport extends PostgreSQLSequenceSupport {

	private final SpannerPostgreSQLDialect dialect;

	public SpannerPostgreSQLSequenceSupport(SpannerPostgreSQLDialect dialect) {
		super();
		this.dialect = dialect;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsPooledSequences() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getCreateSequenceString(String sequenceName, int initialValue, int incrementSize) throws MappingException {
		if ( incrementSize == 0 ) {
			throw new MappingException( "Unable to create the sequence [" + sequenceName + "]: the increment size must not be 0" );
		}
		return getCreateSequenceString( sequenceName )
			+ startingValue( initialValue, incrementSize )
			+ " start counter with " + initialValue;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getRestartSequenceString(String sequenceName, long startWith) {
		return "alter sequence " + sequenceName + " restart counter with " + startWith;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getSelectSequenceNextValString(String sequenceName) {
		var nextValString = super.getSelectSequenceNextValString( sequenceName );
		if (dialect.useIntegerForPrimaryKey()) {
			nextValString = "spanner.bit_reverse(" + nextValString + ", true)";
		}
		return nextValString;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectSequencePreviousValString(String sequenceName) throws MappingException {
		throw new UnsupportedOperationException( "No support for retrieving previous value" );
	}
}
