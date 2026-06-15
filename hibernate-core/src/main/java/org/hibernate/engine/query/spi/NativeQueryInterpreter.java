/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.query.spi;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.sql.internal.NativeSelectQueryPlanImpl;
import org.hibernate.query.sql.spi.NativeSelectQueryDefinition;
import org.hibernate.query.sql.spi.NativeSelectQueryPlan;
import org.hibernate.query.sql.spi.ParameterRecognizer;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Service contract for dealing with native queries.
 *
 * @author Steve Ebersole
 * @author Gunnar Morling
 * @author Guillaume Smet
 */
@Incubating
public interface NativeQueryInterpreter extends Service {
	/**
	 * Parse the given native query and inform the recognizer of all
	 * recognized parameter markers.
	 *
	 * @param nativeQuery The query to recognize parameters in
	 * @param recognizer The recognizer to call
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void recognizeParameters(String nativeQuery, ParameterRecognizer recognizer);

	/**
	 * Creates a new query plan for the passed native query definition
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <R> NativeSelectQueryPlan<R> createQueryPlan(
			NativeSelectQueryDefinition<R> queryDefinition,
			SessionFactoryImplementor sessionFactory) {
		return new NativeSelectQueryPlanImpl<>(
				queryDefinition.getSqlString(),
				queryDefinition.getAffectedTableNames(),
				queryDefinition.getQueryParameterOccurrences(),
				queryDefinition.getResultSetMapping(),
				sessionFactory
		);
	}

}
