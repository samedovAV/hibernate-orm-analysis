/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes the source mapping of plural-attribute (collection) foreign-key information.
 *
 * @author Steve Ebersole
 */
public interface PluralAttributeKeySource
		extends ForeignKeyContributingSource,
				RelationalValueSourceContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedPropertyName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCascadeDeleteEnabled();
}
