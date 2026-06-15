/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;

import org.hibernate.internal.util.ReflectHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class CustomOptimizerDescriptor implements OptimizerDescriptor {
	private final String className;

	CustomOptimizerDescriptor(String className) {
		this.className = className;
	}
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isPooled() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExternalName() {
		return className;
	}

	@Override @SuppressWarnings("unchecked")
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Optimizer> getOptimizerClass() throws ClassNotFoundException {
		return (Class<? extends Optimizer>) ReflectHelper.classForName( className );
	}
}
