/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.dynamic;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.results.spi.ResultBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ResultBuilder specialization for results added through the Hibernate-specific
 * {@link NativeQuery} result definition methods.
 *
 * @see NativeQuery#addScalar
 * @see NativeQuery#addInstantiation
 * @see NativeQuery#addAttributeResult
 * @see NativeQuery#addEntity
 * @see NativeQuery#addRoot
 *
 * @author Steve Ebersole
 */
public interface DynamicResultBuilder extends ResultBuilder, NativeQuery.ReturnableResultNode {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DynamicResultBuilder cacheKeyInstance();
}
