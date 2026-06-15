/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.Type;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes any non-collection type.
 *
 * @author Steve Ebersole
 */
public interface SimpleDomainType<J> extends DomainType<J>, Type<J> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Class<J> getJavaType() {
		return getExpressibleJavaType().getJavaTypeClass();
	}
}
