/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.procedure.spi.NamedCallableQueryMemento;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Boot-time descriptor of a named procedure/function query, as defined in
 * annotations or xml
 *
 * @see jakarta.persistence.NamedStoredProcedureQuery
 *
 * @author Steve Ebersole
 */
public interface NamedProcedureCallDefinition extends NamedQueryDefinition<Object> {
	/**
	 * The name of the underlying database procedure or function name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getProcedureName();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedCallableQueryMemento resolve(SessionFactoryImplementor factory);
}
