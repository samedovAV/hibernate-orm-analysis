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
 * Return a standalone JTA transaction manager for Narayana (Arjuna) Transactions.
 *
 * @author Emmanuel Bernard
 * @author Steve Ebersole
 */
public class NarayanaJtaPlatform extends AbstractJtaPlatform {

	public static final String TM_CLASS_NAME = "com.arjuna.ats.jta.TransactionManager";
	public static final String UT_CLASS_NAME = "com.arjuna.ats.jta.UserTransaction";

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected TransactionManager locateTransactionManager() {
		try {
			return (TransactionManager) serviceRegistry().requireService( ClassLoaderService.class )
					.classForName( TM_CLASS_NAME )
					.getMethod( "transactionManager" )
					.invoke( null );
		}
		catch ( Exception e ) {
			throw new JtaPlatformException( "Could not obtain Narayana TransactionManager instance", e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected UserTransaction locateUserTransaction() {
		try {
			return (UserTransaction) serviceRegistry()
					.requireService( ClassLoaderService.class )
					.classForName( UT_CLASS_NAME )
					.getMethod( "userTransaction" )
					.invoke( null );
		}
		catch ( Exception e ) {
			throw new JtaPlatformException( "Could not obtain Narayana UserTransaction instance", e );
		}
	}
}
