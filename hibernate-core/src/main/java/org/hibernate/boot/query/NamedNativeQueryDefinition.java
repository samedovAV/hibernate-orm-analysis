/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.query;


import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.named.NamedNativeQueryMemento;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Boot-time descriptor of a named native query.
 *
 * @see org.hibernate.annotations.NamedNativeQuery
 * @see jakarta.persistence.NamedNativeQuery
 * @see jakarta.persistence.NamedNativeStatement
 *
 * @author Steve Ebersole
 * @author Gavin King
 */
public interface NamedNativeQueryDefinition<E> extends NamedQueryDefinition<E> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlQueryString();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getResultSetMappingName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getQuerySpaces();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedNativeQueryMemento<E> resolve(SessionFactoryImplementor factory);

}
