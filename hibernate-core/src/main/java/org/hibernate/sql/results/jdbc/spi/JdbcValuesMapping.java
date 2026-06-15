/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.jdbc.spi;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.ast.spi.SqlSelection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The "resolved" form of {@link JdbcValuesMappingProducer} providing access
 * to resolved JDBC results ({@link SqlSelection}) descriptors and resolved
 * domain results ({@link DomainResult}) descriptors.
 *
 * @see JdbcValuesMappingProducer#resolve
 *
 * @author Steve Ebersole
 */
public interface JdbcValuesMapping {
	/**
	 * The JDBC selection descriptors.  Used to read ResultSet values and build
	 * the "JDBC values array"
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<SqlSelection> getSqlSelections();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getRowSize();

	/**
	 * Mapping from value index to cache index.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int[] getValueIndexesToCacheIndexes();

	/**
	 * The size of the row for caching.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getRowToCacheSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<DomainResult<?>> getDomainResults();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcValuesMappingResolution resolveAssemblers(SessionFactoryImplementor sessionFactory);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	LockMode determineDefaultLockMode(String alias, LockMode defaultLockMode);

}
