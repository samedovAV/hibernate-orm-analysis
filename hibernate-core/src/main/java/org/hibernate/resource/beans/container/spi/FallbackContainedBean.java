/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.resource.beans.container.spi;

import org.hibernate.resource.beans.spi.BeanInstanceProducer;
import org.hibernate.resource.beans.spi.ManagedBean;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class FallbackContainedBean<B> implements ContainedBean<B>, ManagedBean<B> {
	private final Class<B> beanType;

	private final B beanInstance;


	public FallbackContainedBean(Class<B> beanType, BeanInstanceProducer producer) {
		this.beanType = beanType;
		this.beanInstance = producer.produceBeanInstance( beanType );
	}

	public FallbackContainedBean(String beanName, Class<B> beanType, BeanInstanceProducer producer) {
		this.beanType = beanType;
		this.beanInstance = producer.produceBeanInstance( beanName, beanType );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<B> getBeanClass() {
		return beanType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public B getBeanInstance() {
		return beanInstance;
	}
}
