/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.selector.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface StrategyCreator<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	T create(Class<? extends T> strategyClass);
}
