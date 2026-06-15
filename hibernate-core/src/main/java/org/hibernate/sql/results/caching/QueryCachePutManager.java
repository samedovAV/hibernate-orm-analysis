/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.caching;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface QueryCachePutManager {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerJdbcRow(Object values);

	/**
	 * @since 6.6
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp(int resultCount, SharedSessionContractImplementor session);
}
