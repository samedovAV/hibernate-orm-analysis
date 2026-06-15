/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.model.CustomSql;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface PluralAttributeSource
		extends AttributeSource,
				FetchableAttributeSource,
				CascadeStyleSource {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeNature getNature();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionIdSource getCollectionIdSource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeKeySource getKeySource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeElementSource getElementSource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FilterSource[] getFilterSources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableSpecificationSource getCollectionTableSpecificationSource();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCollectionTableComment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCollectionTableCheck();

	/**
	 * Obtain any additional table names on which to synchronize (auto flushing) this entity.
	 *
	 * @return Additional synchronized table names or 0 sized String array, never return null.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getSynchronizedTableNames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Caching getCaching();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomPersisterClassName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWhere();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInverse();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomLoaderName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlInsert();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlUpdate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlDelete();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlDeleteAll();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getMappedBy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean usesJoinTable();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchCharacteristicsPluralAttribute getFetchCharacteristics();
}
