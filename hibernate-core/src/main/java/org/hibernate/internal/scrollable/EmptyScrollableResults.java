/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.scrollable;

import org.hibernate.ScrollableResults;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class EmptyScrollableResults<R> implements ScrollableResults<R> {

	@SuppressWarnings("rawtypes")
	private static final ScrollableResults INSTANCE = new EmptyScrollableResults();

	@SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static <R> EmptyScrollableResults<R> instance() {
		return (EmptyScrollableResults<R>) INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isClosed() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void close() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean next() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean previous() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean scroll(int positions) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean position(int position) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean last() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean first() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void beforeFirst() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void afterLast() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isFirst() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isLast() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getRowNumber() {
		return -1;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getPosition() {
		return 0;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean setRowNumber(int rowNumber) {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setFetchSize(int fetchSize) {}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public R get() {
		throw new UnsupportedOperationException( "Empty result set" );
	}
}
