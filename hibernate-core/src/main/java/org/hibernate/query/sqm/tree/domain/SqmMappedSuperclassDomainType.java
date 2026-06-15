/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import jakarta.annotation.Nullable;
import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.MappedSuperclassDomainType;
import org.hibernate.query.sqm.SqmPathSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface SqmMappedSuperclassDomainType<T>
		extends MappedSuperclassDomainType<T>, SqmPathSource<T>, SqmManagedDomainType<T> {
	@Override
	@Nullable@Prove(complexity = Complexity.O_1, n = "", count = {})
 SqmDomainType<T> getSqmType();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();
}
