/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.transaction.jta.platform.internal;

import jakarta.transaction.TransactionManager;
import jakarta.transaction.UserTransaction;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * {@link org.hibernate.engine.transaction.jta.platform.spi.JtaPlatform} implementation for Weblogic
 *
 * @author Gavin King
 * @author Steve Ebersole
 */
public class WeblogicJtaPlatform extends AbstractJtaPlatform {
	public static final String TM_NAME = "jakarta.transaction.TransactionManager";
	public static final String UT_NAME = "jakarta.transaction.UserTransaction";

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected TransactionManager locateTransactionManager() {
		return (TransactionManager) jndiService().locate( TM_NAME );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected UserTransaction locateUserTransaction() {
		return (UserTransaction) jndiService().locate( UT_NAME );
	}
}
