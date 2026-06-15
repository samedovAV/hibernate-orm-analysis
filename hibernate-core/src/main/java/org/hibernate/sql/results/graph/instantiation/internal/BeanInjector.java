/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.instantiation.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Unified contract for injecting a single argument for a dynamic instantiation
 * result, whether that is constructor-based or setter-based.
 *
 * @author Steve Ebersole
 */
interface BeanInjector<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void inject(T target, Object value);
}
