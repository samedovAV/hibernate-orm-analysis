/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.results.internal.jpa;

import org.hibernate.query.results.spi.ResultBuilder;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Adapter for [jakarta.persistence.sql.MappingElement] as a
/// [org.hibernate.query.results.spi.ResultBuilder].
///
/// @author Steve Ebersole
public interface MappingElementBuilder<T> extends ResultBuilder {
	/// @see jakarta.persistence.sql.MappingElement#getAlias()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAlias();

	/// @see jakarta.persistence.sql.MappingElement#getJavaType()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends T> getJavaType();
}
