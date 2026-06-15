/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A proxy factory for "basic proxy" generation.
 *
 * @author Steve Ebersole
 */
public interface BasicProxyFactory {
	/**
	 * Get a proxy reference..
	 *
	 * @return A proxy reference.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getProxy();
}
