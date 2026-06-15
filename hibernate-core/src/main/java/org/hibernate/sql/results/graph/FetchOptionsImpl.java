/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

class FetchOptionsImpl implements FetchOptions {
	private final FetchTiming fetchTiming;
	private final FetchStyle fetchStyle;

	FetchOptionsImpl(FetchTiming fetchTiming, FetchStyle fetchStyle) {
		this.fetchTiming = fetchTiming;
		this.fetchStyle = fetchStyle;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchStyle getStyle() {
		return fetchStyle;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchTiming getTiming() {
		return fetchTiming;
	}
}
