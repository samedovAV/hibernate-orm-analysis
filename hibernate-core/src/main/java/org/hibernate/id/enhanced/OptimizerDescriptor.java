/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Gavin King
 */
public interface OptimizerDescriptor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isPooled();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExternalName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends Optimizer> getOptimizerClass()
			throws ClassNotFoundException;
}
