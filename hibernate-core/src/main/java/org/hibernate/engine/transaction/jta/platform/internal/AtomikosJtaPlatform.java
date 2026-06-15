/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.engine.transaction.jta.platform.spi.JtaPlatformException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Vlad Mihalcea
 */
public class AtomikosJtaPlatform extends AbstractJtaPlatform {
	public static final String TM_CLASS_NAME = "com.atomikos.icatch.jta.UserTransactionManager";

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected TransactionManager locateTransactionManager() {
		try {
			return (TransactionManager) serviceRegistry()
					.requireService( ClassLoaderService.class )
					.classForName( TM_CLASS_NAME )
					.newInstance();
		}
		catch (Exception e) {
			throw new JtaPlatformException( "Could not instantiate Atomikos TransactionManager", e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected UserTransaction locateUserTransaction() {
		return (UserTransaction) jndiService().locate( "java:comp/UserTransaction" );
	}
}
