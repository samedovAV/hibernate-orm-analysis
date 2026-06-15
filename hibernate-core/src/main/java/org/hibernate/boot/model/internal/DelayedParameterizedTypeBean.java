/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.Properties;

import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.usertype.ParameterizedType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ManagedBean implementation for delayed {@link ParameterizedType}
 * handling (parameter injection) for a UserCollectionType
 *
 * @author Steve Ebersole
 */
public class DelayedParameterizedTypeBean<T> implements ManagedBean<T> {
	private final ManagedBean<T> underlyingBean;
	private final Properties properties;

	private T instance;

	public DelayedParameterizedTypeBean(ManagedBean<T> underlyingBean, Properties properties) {
		assert ParameterizedType.class.isAssignableFrom( underlyingBean.getBeanClass() );
		this.underlyingBean = underlyingBean;
		this.properties = properties;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Class<T> getBeanClass() {
		return underlyingBean.getBeanClass();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public T getBeanInstance() {
		if ( instance == null ) {
			instance = underlyingBean.getBeanInstance();
			( (ParameterizedType) instance ).setParameterValues( properties );
		}
		return instance;
	}
}
