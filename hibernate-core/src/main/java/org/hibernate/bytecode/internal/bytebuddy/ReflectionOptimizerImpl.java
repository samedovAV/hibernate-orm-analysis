/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal.bytebuddy;

import java.io.Serializable;

import org.hibernate.bytecode.spi.ReflectionOptimizer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ReflectionOptimizerImpl implements ReflectionOptimizer, Serializable {
	private final InstantiationOptimizer instantiationOptimizer;
	private final AccessOptimizer accessOptimizer;

	public ReflectionOptimizerImpl(
			InstantiationOptimizer instantiationOptimizer,
			AccessOptimizer accessOptimizer) {
		this.instantiationOptimizer = instantiationOptimizer;
		this.accessOptimizer = accessOptimizer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public InstantiationOptimizer getInstantiationOptimizer() {
		return instantiationOptimizer;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AccessOptimizer getAccessOptimizer() {
		return accessOptimizer;
	}

}
