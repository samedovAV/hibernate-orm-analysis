/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Sequence support for {@link org.hibernate.dialect.HANADialect}.
 *
 * @author Gavin King
 */
public final class HANASequenceSupport extends NextvalSequenceSupport {

	public static final SequenceSupport INSTANCE = new HANASequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getFromDual() {
		return " from sys.dummy";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean sometimesNeedsStartingValue() {
		return true;
	}
}
