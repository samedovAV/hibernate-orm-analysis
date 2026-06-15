/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sequence support for {@link org.hibernate.dialect.DB2iDialect}.
 *
 * @author Christian Beikov
 */
public class DB2iSequenceSupport implements SequenceSupport {

	public static final SequenceSupport INSTANCE = new DB2iSequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectSequenceNextValString(String sequenceName) {
		return "nextval for " + sequenceName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectSequencePreviousValString(String sequenceName) throws MappingException {
		return "prevval for " + sequenceName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSequenceNextValString(String sequenceName) {
		return "values " + getSelectSequenceNextValString( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSequencePreviousValString(String sequenceName) throws MappingException {
		return "values " + getSelectSequencePreviousValString( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDropSequenceString(String sequenceName) {
		return "drop sequence " + sequenceName + " restrict";
	}
}
