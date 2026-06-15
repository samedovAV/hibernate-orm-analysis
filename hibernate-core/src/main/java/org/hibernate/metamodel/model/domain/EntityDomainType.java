/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain;

import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.persistence.metamodel.EntityType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Extension to the JPA {@link EntityType} contract.
 *
 * @author Steve Ebersole
 */
public interface EntityDomainType<J> extends IdentifiableDomainType<J>, EntityType<J>, TreatableDomainType<J> {
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getHibernateEntityName();

	@Override
	@Nonnull
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<? extends EntityDomainType<? extends J>> getSubTypes();
}
