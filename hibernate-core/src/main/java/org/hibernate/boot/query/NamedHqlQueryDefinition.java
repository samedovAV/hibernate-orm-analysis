/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.named.NamedSqmQueryMemento;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Boot-time descriptor of a named query define using HQL.
 *
 * @see jakarta.persistence.NamedQuery
 * @see jakarta.persistence.NamedStatement
 * @see org.hibernate.annotations.NamedQuery
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
public interface NamedHqlQueryDefinition<E> extends NamedQueryDefinition<E> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getHqlString();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedSqmQueryMemento<E> resolve(SessionFactoryImplementor factory);

}
