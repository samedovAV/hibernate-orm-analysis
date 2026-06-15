/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


// Used by Hibernate Reactive
public interface InterpretationsKeySource extends CacheabilityInfluencers {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?> getResultType();
}
