/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ANSI SQL compliant sequence support, for dialects which
 * support the ANSI SQL syntax {@code next value for seqname}.
 *
 * @author Gavin King
 */
public class ANSISequenceSupport implements SequenceSupport {

	public static final SequenceSupport INSTANCE = new ANSISequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public final String getSelectSequenceNextValString(String sequenceName) {
		return "next value for " + sequenceName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSelectSequencePreviousValString(String sequenceName) throws MappingException {
		return "current value for " + sequenceName;
	}
}
