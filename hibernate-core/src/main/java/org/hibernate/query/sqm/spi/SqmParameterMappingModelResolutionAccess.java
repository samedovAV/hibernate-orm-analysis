/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.spi;

import org.hibernate.metamodel.mapping.MappingModelExpressible;
import org.hibernate.query.sqm.tree.expression.SqmParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface SqmParameterMappingModelResolutionAccess {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> MappingModelExpressible<T> getResolvedMappingModelType(SqmParameter<T> parameter);
}
