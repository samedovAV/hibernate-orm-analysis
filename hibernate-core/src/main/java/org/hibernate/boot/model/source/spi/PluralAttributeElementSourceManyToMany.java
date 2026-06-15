/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes the source for the elements of persistent collections (plural
 * attributes) where the elements are many-to-many association
 *
 * @author Steve Ebersole
 */
public interface PluralAttributeElementSourceManyToMany
		extends PluralAttributeElementSourceAssociation, RelationalValueSourceContainer,
				ForeignKeyContributingSource, Orderable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityAttributeName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIgnoreNotFound();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitForeignKeyName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUnique();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FilterSource[] getFilterSources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWhere();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchCharacteristics getFetchCharacteristics();
}
