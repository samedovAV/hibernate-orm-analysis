/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.BasicType;

import org.hibernate.type.OutputableType;

import static jakarta.persistence.metamodel.Type.PersistenceType.BASIC;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Hibernate extension to the JPA {@link BasicType} contract.
 *
 * @author Steve Ebersole
 */
public interface BasicDomainType<J>
		extends ReturnableType<J>, BasicType<J>, OutputableType<J> {
	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default PersistenceType getPersistenceType() {
		return BASIC;
	}

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Class<J> getJavaType() {
		return ReturnableType.super.getJavaType();
	}
}
