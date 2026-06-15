/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.hibernate.query.criteria.JpaQueryableCriteria;
import org.hibernate.query.sqm.SqmQuerySource;
import org.hibernate.query.sqm.tree.expression.JpaCriteriaParameter;
import org.hibernate.query.sqm.tree.expression.SqmJpaCriteriaParameterWrapper;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The basic SQM statement contract for top-level statements
 *
 * @author Steve Ebersole
 */
public interface SqmStatement<T> extends SqmQuery<T>, JpaQueryableCriteria<T>, SqmVisitableNode {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmQuerySource getQuerySource();

	/**
	 * Access to the (potentially still growing) collection of parameters for the statement.
	 *
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<SqmParameter<?>> getSqmParameters();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterResolutions resolveParameters();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmStatement<T> copy(SqmCopyContext context);

	interface ParameterResolutions {
		ParameterResolutions EMPTY = new ParameterResolutions() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Set<SqmParameter<?>> getSqmParameters() {
				return Collections.emptySet();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Map<JpaCriteriaParameter<?>, SqmJpaCriteriaParameterWrapper<?>> getJpaCriteriaParamResolutions() {
				return Collections.emptyMap();
			}
		};

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		static ParameterResolutions empty() {
			return EMPTY;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Set<SqmParameter<?>> getSqmParameters();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		Map<JpaCriteriaParameter<?>, SqmJpaCriteriaParameterWrapper<?>> getJpaCriteriaParamResolutions();
	}
}
