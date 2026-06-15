/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.graph.instantiation.internal;

import org.hibernate.sql.results.graph.DomainResultAssembler;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class BeanInjection {
	private final BeanInjector beanInjector;
	private final DomainResultAssembler valueAssembler;

	public BeanInjection(BeanInjector beanInjector, DomainResultAssembler valueAssembler) {
		this.beanInjector = beanInjector;
		this.valueAssembler = valueAssembler;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BeanInjector getBeanInjector() {
		return beanInjector;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public DomainResultAssembler getValueAssembler() {
		return valueAssembler;
	}
}
