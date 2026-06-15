/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.cache.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Optional contract for a {@link Region} defining support for extra statistic information.
 *
 * @author Steve Ebersole
 */
public interface ExtendedStatisticsSupport {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getElementCountInMemory();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getElementCountOnDisk();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getSizeInMemory();
}
