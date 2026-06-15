/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.DomainType;
import org.hibernate.query.sqm.SqmBindableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface SqmDomainType<T>
		extends DomainType<T>, SqmBindableType<T> {

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default String getTypeName() {
		return SqmBindableType.super.getTypeName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default int getTupleLength() {
		return 1;
	}
}
