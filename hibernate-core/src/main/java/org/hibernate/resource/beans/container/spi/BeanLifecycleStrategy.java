/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.container.spi;

import org.hibernate.resource.beans.spi.BeanInstanceProducer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Models how the lifecycle for a bean should be managed.
 */
public interface BeanLifecycleStrategy {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<B> ContainedBeanImplementor<B> createBean(
			Class<B> beanClass,
			BeanInstanceProducer fallbackProducer,
			BeanContainer beanContainer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<B> ContainedBeanImplementor<B> createBean(
			String beanName,
			Class<B> beanClass,
			BeanInstanceProducer fallbackProducer,
			BeanContainer beanContainer);
}
