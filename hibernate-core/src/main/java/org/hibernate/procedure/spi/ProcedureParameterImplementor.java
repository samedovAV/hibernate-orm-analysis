/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.spi;

import org.hibernate.Incubating;
import org.hibernate.procedure.ProcedureParameter;
import org.hibernate.query.spi.QueryParameterImplementor;
import org.hibernate.sql.exec.spi.JdbcCallParameterRegistration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI extension for ProcedureParameter
 *
 * @author Steve Ebersole
 */
@Incubating
public interface ProcedureParameterImplementor<T> extends ProcedureParameter<T>, QueryParameterImplementor<T> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcCallParameterRegistration toJdbcParameterRegistration(int startIndex, ProcedureCallImplementor<?> procedureCall);

}
