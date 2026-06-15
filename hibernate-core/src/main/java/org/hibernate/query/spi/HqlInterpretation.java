/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import org.hibernate.query.sqm.internal.DomainParameterXref;
import org.hibernate.query.sqm.tree.SqmStatement;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 *
 * @param <R> the query result type
 */
public interface HqlInterpretation<R> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmStatement<R> getSqmStatement();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterMetadataImplementor getParameterMetadata();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DomainParameterXref getDomainParameterXref();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validateResultType(Class<?> resultType);

}
