/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sequence support for {@link org.hibernate.dialect.DB2Dialect}.
 *
 * @author Gavin King
 */
public class DB2SequenceSupport extends ANSISequenceSupport {

	public static final SequenceSupport INSTANCE = new DB2SequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSequenceNextValString(String sequenceName) {
		return "values " + getSelectSequenceNextValString( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectSequencePreviousValString(String sequenceName) throws MappingException {
		return "previous value for " + sequenceName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSequencePreviousValString(String sequenceName) {
		return "values " + getSelectSequencePreviousValString( sequenceName );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDropSequenceString(String sequenceName) {
		return "drop sequence " + sequenceName + " restrict";
	}
}
