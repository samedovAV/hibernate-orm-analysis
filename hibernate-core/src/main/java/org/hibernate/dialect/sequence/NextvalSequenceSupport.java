/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Sequence support for dialects which support the common
 * Oracle-style syntax {@code seqname.nextval}.
 */
public class NextvalSequenceSupport implements SequenceSupport {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final String getSelectSequenceNextValString(String sequenceName) {
		return sequenceName + ".nextval";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final String getSelectSequencePreviousValString(String sequenceName) {
		return sequenceName + ".currval";
	}

}
