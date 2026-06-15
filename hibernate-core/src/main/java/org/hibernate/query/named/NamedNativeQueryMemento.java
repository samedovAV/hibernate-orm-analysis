/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import java.util.Set;

import org.hibernate.engine.spi.SharedSessionContractImplementor;

import jakarta.persistence.SqlResultSetMapping;
import org.hibernate.query.sql.spi.NativeQueryImplementor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Descriptor for a named native query in the runtime environment
 *
 * @author Steve Ebersole
 */
public interface NamedNativeQueryMemento<E> extends NamedQueryMemento<E> {
	/**
	 * Informational access to the SQL query string
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSqlString();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getOriginalSqlString(){
		return getSqlString();
	}

	/**
	 * The affected query spaces.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getQuerySpaces();

	/**
	 * An explicit ResultSet mapping by name
	 *
	 * @see SqlResultSetMapping#name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getResultMappingName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getFirstResult();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getMaxResults();


	/**
	 * Convert the memento into an untyped executable query
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NativeQueryImplementor<E> toQuery(SharedSessionContractImplementor session);

	/**
	 * Convert the memento into a typed executable query
	 */
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> NativeQueryImplementor<T> toQuery(SharedSessionContractImplementor session, Class<T> resultType);

	/**
	 * Convert the memento into a typed executable query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> NativeQueryImplementor<T> toQuery(SharedSessionContractImplementor session, String resultSetMapping);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedNativeQueryMemento<E> makeCopy(String name);

}
