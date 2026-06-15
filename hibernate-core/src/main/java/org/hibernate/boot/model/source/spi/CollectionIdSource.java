/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface CollectionIdSource {
	/**
	 * Obtain source information about the column for the collection id.
	 *
	 * @return The collection id column info.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ColumnSource getColumnSource();

	/**
	 * Obtain information about the Hibernate type ({@link org.hibernate.type.Type}) for the collection id
	 *
	 * @return The Hibernate type information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateTypeSource getTypeInformation();

	/**
	 * Obtain the name of the identifier value generator.
	 *
	 * @return The identifier value generator name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getGeneratorName();

	/**
	 * @return The identifier generator configuration parameters
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, String> getParameters();
}
