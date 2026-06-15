/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.EntityDomainType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface SqmEntityDomainType<E> extends EntityDomainType<E>, SqmTreatableDomainType<E> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default @Nullable SqmDomainType<E> getSqmType() {
		return this;
	}
}
