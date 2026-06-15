/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import org.hibernate.Incubating;
import org.hibernate.ScrollableResults;
import org.hibernate.query.spi.CloseableIterator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 *
 * @since 5.2
 */
@Incubating
public class ScrollableResultsIterator<T> implements CloseableIterator<T> {
	private final ScrollableResults<T> scrollableResults;

	public ScrollableResultsIterator(ScrollableResults<T> scrollableResults) {
		this.scrollableResults = scrollableResults;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void close() {
		scrollableResults.close();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasNext() {
		return !scrollableResults.isClosed() && scrollableResults.next();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T next() {
		return scrollableResults.get();
	}
}
