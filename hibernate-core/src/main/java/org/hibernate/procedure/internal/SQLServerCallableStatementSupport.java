/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.internal;

import org.hibernate.procedure.spi.ProcedureParameterImplementor;
import org.hibernate.sql.exec.spi.JdbcCallParameterRegistration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SQLServerCallableStatementSupport extends StandardCallableStatementSupport {

	public static final StandardCallableStatementSupport INSTANCE = new SQLServerCallableStatementSupport( );


	private SQLServerCallableStatementSupport() {
		super( false );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void appendNameParameter(
			StringBuilder buffer,
			ProcedureParameterImplementor<?> parameter,
			JdbcCallParameterRegistration registration) {
		buffer.append( '@' ).append( parameter.getName() ).append( " = ?" );
	}
}
