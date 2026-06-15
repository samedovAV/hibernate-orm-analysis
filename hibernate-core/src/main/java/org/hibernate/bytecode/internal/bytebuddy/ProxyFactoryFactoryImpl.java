/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal.bytebuddy;

import org.hibernate.bytecode.spi.BasicProxyFactory;
import org.hibernate.bytecode.spi.ProxyFactoryFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyProxyFactory;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyProxyHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class ProxyFactoryFactoryImpl implements ProxyFactoryFactory {

	private final ByteBuddyState byteBuddyState;

	private final ByteBuddyProxyHelper byteBuddyProxyHelper;

	public ProxyFactoryFactoryImpl(ByteBuddyState byteBuddyState, ByteBuddyProxyHelper byteBuddyProxyHelper) {
		this.byteBuddyState = byteBuddyState;
		this.byteBuddyProxyHelper = byteBuddyProxyHelper;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ProxyFactory buildProxyFactory(SessionFactoryImplementor sessionFactory) {
		return new ByteBuddyProxyFactory( byteBuddyProxyHelper );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public BasicProxyFactory buildBasicProxyFactory(Class superClassOrInterface) {
		if ( superClassOrInterface.isInterface() ) {
			return new BasicProxyFactoryImpl( null, superClassOrInterface, byteBuddyState );
		}
		else {
			return new BasicProxyFactoryImpl( superClassOrInterface, null, byteBuddyState );
		}
	}

}
