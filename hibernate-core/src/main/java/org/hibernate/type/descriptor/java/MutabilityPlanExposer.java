/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.descriptor.java;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Something that exposes a MutabilityPlan
 */
public interface MutabilityPlanExposer<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutabilityPlan<T> getExposedMutabilityPlan();
}
