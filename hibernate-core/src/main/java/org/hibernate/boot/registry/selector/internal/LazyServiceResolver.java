/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.selector.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


@FunctionalInterface
public interface LazyServiceResolver<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends T> resolve(String name);

}
