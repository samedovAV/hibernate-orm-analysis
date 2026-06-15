/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import java.util.function.Consumer;
import java.util.function.Predicate;
import jakarta.persistence.Parameter;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.ParameterMetadata;
import org.hibernate.query.QueryParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface ParameterMetadataImplementor extends ParameterMetadata {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitParameters(Consumer<QueryParameter<?>> consumer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void collectAllParameters(Consumer<QueryParameter<?>> collector) {
		visitParameters( collector );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void visitRegistrations(Consumer<QueryParameter<?>> action) {
		visitParameters( action );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasAnyMatching(Predicate<QueryParameterImplementor<?>> filter);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterImplementor<?> findQueryParameter(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterImplementor<?> getQueryParameter(String name);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterImplementor<?> findQueryParameter(int positionLabel);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterImplementor<?> getQueryParameter(int positionLabel);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<P> QueryParameterImplementor<P> resolve(Parameter<P> param);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QueryParameterBindings createBindings(SessionFactoryImplementor sessionFactory);
}
