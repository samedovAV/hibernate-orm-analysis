/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal.none;

import org.hibernate.bytecode.spi.BasicProxyFactory;
import org.hibernate.bytecode.spi.ProxyFactoryFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.proxy.ProxyFactory;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * When entities are enhanced in advance, proxies are not needed.
 */
final class NoProxyFactoryFactory implements ProxyFactoryFactory {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ProxyFactory buildProxyFactory(SessionFactoryImplementor sessionFactory) {
		return DisallowedProxyFactory.INSTANCE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicProxyFactory buildBasicProxyFactory(Class superClassOrInterface) {
		return new NoneBasicProxyFactory( superClassOrInterface );
	}
}
