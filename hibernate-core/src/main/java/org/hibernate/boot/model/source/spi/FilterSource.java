/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the source of filter information.  May have an associated {@link org.hibernate.engine.spi.FilterDefinition}.
 * Relates to both {@code <filter/>} and {@link org.hibernate.annotations.Filter @Filter}
 *
 * @author Steve Ebersole
 */
public interface FilterSource {
	/**
	 * Get the name of the filter being described.
	 *
	 * @return The name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * Get the condition associated with the filter.  Can be {@code null} in the case of a filter described
	 * further by a "filter def" which contains the condition text.
	 *
	 * @return The condition defined on the filter.
	 *
	 * @see org.hibernate.boot.model.source.internal.hbm.FilterSourceImpl#getCondition()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCondition();

	/**
	 * Should Hibernate perform automatic alias injection into the supplied condition string?  The default is to
	 * perform auto injection *unless* explicit alias(es) are supplied.
	 *
	 * @return {@code true} indicates auto injection should occur; {@code false} that it should not
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldAutoInjectAliases();

	/**
	 * Get the map of explicit alias to table name mappings.
	 *
	 * @return The alias to table map
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, String> getAliasToTableMap();

	/**
	 * Get the map of explicit alias to entity name mappings.
	 *
	 * @return The alias to entity map
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, String> getAliasToEntityMap();
}
