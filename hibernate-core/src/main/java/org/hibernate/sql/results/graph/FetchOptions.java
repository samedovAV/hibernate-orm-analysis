/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph;

import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents an aggregated {@link FetchTiming} and {@link FetchStyle} value
 *
 * @author Steve Ebersole
 */
public interface FetchOptions extends FetchTimingAccess, FetchStyleAccess {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static FetchOptions valueOf(FetchTiming fetchTiming, FetchStyle fetchStyle) {
		return new FetchOptionsImpl( fetchTiming, fetchStyle );
	}
}
