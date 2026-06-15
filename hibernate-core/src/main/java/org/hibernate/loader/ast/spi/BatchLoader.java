/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Commonality for loading a {@linkplain Loadable loadable} in "batch" (more than one key at a time)
 *
 * @author Steve Ebersole
 */
public interface BatchLoader extends MultiKeyLoader {
	/**
	 * The total number of {@linkplain Loadable loadable} references that can be initialized per each load operation.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getDomainBatchSize();
}
