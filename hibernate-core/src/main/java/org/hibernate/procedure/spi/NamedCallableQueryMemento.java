/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.spi;

import java.util.List;
import java.util.Set;

import org.hibernate.Incubating;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.named.NamedQueryMemento;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Represents a "memento" (disconnected, externalizable form) of a ProcedureCall
 *
 * @author Steve Ebersole
 */
@Incubating
public interface NamedCallableQueryMemento extends NamedQueryMemento<Object> {
	/**
	 * Informational access to the name of the database function or procedure
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCallableName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ParameterMemento> getParameterMementos();

	/**
	 * Convert the memento back into an executable (connected) form.
	 *
	 * @param session The session to connect the procedure call to
	 *
	 * @return The executable call
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default ProcedureCall makeProcedureCall(Session session) {
		return makeProcedureCall( (SharedSessionContractImplementor) session );
	}

	/**
	 * Convert the memento back into an executable (connected) form.
	 *
	 * @param session The session to connect the procedure call to
	 *
	 * @return The executable call
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default ProcedureCall makeProcedureCall(SessionImplementor session) {
		return makeProcedureCall( (SharedSessionContractImplementor) session );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterStrategy getParameterStrategy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getResultSetMappingNames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<?>[] getResultSetMappingClasses();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getQuerySpaces();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor toQuery(SharedSessionContractImplementor session);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<X> ProcedureCallImplementor<X> toQuery(SharedSessionContractImplementor session, Class<X> javaType);

	/**
	 * Convert the memento back into an executable (connected) form.
	 *
	 * @param session The session to connect the procedure call to
	 *
	 * @return The executable call
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor makeProcedureCall(SharedSessionContractImplementor session);

	/**
	 * Convert the memento back into an executable (connected) form.
	 *
	 * @param session The session to connect the procedure call to
	 *
	 * @return The executable call
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureCallImplementor makeProcedureCall(SharedSessionContractImplementor session, String... resultSetMappingNames);

	interface ParameterMemento extends NamedQueryMemento.ParameterMemento {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		ProcedureParameterImplementor<?> resolve(SharedSessionContractImplementor session);
	}
}
