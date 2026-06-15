/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.internal;

import org.hibernate.procedure.spi.ProcedureParameterImplementor;
import org.hibernate.sql.exec.spi.JdbcCallParameterRegistration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard implementation of {@link org.hibernate.procedure.spi.CallableStatementSupport}.
 *
 * @author Steve Ebersole
 */
public class OracleCallableStatementSupport extends StandardCallableStatementSupport {

	public static final StandardCallableStatementSupport REF_CURSOR_INSTANCE = new OracleCallableStatementSupport( true );


	public OracleCallableStatementSupport(boolean supportsRefCursors) {
		super( supportsRefCursors );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void appendNameParameter(
			StringBuilder buffer,
			ProcedureParameterImplementor<?> parameter,
			JdbcCallParameterRegistration registration) {
		buffer.append( parameter.getName() ).append( " => ?" );
	}

}
