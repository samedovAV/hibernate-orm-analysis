/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Highly abstract concept of the index of an "indexed persistent collection".
 * More concretely (and generally more usefully) categorized as either:<ul>
 *     <li>{@link PluralAttributeSequentialIndexSource} - for list/array indexes</li>
 *     <li>{@link PluralAttributeMapKeySource} - for map keys</li>
 * </ul>
 *
 */
public interface PluralAttributeIndexSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeIndexNature getNature();

	/**
	 * Obtain information about the Hibernate index type ({@link org.hibernate.type.Type})
	 * for this plural attribute index.
	 *
	 * @return The Hibernate type information
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateTypeSource getTypeInformation();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getXmlNodeName();
}
