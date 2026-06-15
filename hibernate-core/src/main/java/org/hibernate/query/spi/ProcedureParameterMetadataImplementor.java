/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.List;

import jakarta.persistence.Parameter;
import org.hibernate.procedure.spi.ParameterStrategy;
import org.hibernate.procedure.spi.ProcedureParameterImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public interface ProcedureParameterMetadataImplementor extends ParameterMetadataImplementor {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterStrategy getParameterStrategy();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureParameterImplementor<?> getQueryParameter(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ProcedureParameterImplementor<?> getQueryParameter(int positionLabel);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> ProcedureParameterImplementor<P> resolve(Parameter<P> parameter);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerParameter(ProcedureParameterImplementor<?> parameter);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<? extends ProcedureParameterImplementor<?>> getRegistrationsAsList();

}
