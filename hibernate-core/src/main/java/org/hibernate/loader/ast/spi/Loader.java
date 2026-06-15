/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Common contract for all value-mapping loaders.
 *
 * @author Steve Ebersole
 */
public interface Loader {
	/**
	 * The value-mapping loaded by this loader
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Loadable getLoadable();
}
