/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jdbc;
import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Indicates that more rows were affected than we were expecting to be.
 * Typically indicates presence of duplicate "PK" values in the
 * given table.
 *
 * @author Steve Ebersole
 */
public class TooManyRowsAffectedException extends HibernateException {
	private final int expectedRowCount;
	private final int actualRowCount;

	public TooManyRowsAffectedException(String message, int expectedRowCount, int actualRowCount) {
		super( message );
		this.expectedRowCount = expectedRowCount;
		this.actualRowCount = actualRowCount;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getExpectedRowCount() {
		return expectedRowCount;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getActualRowCount() {
		return actualRowCount;
	}
}
