/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.bytecode.internal.none;

import java.lang.reflect.Method;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.type.CompositeType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

final class DisallowedProxyFactory implements ProxyFactory {

	static final DisallowedProxyFactory INSTANCE = new DisallowedProxyFactory();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void postInstantiate(
			String entityName,
			Class<?> persistentClass,
			Set<Class<?>> interfaces,
			Method getIdentifierMethod,
			Method setIdentifierMethod,
			CompositeType componentIdType) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateProxy getProxy(Object id, SharedSessionContractImplementor session) throws HibernateException {
		throw new HibernateException( "Generation of HibernateProxy instances at runtime is not allowed when the configured BytecodeProvider is 'none'; your model requires a more advanced BytecodeProvider to be enabled." );
	}

}
