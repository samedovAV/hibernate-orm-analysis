/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between the various forms of plural attribute (collection) mappings: {@code <bag/>}, {@code <set/>}, etc.
 *
 * @author Steve Ebersole
 */
public interface PluralAttributeInfo extends AttributeMapping, TableInformationContainer, ToolingHintContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmKeyType getKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmBasicCollectionElementType getElement();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCompositeCollectionElementType getCompositeElement();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmOneToManyCollectionElementType getOneToMany();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmManyToManyCollectionElementType getManyToMany();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmManyToAnyCollectionElementType getManyToAny();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComment();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCheck();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWhere();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmLoaderType getLoader();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlInsert();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlUpdate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlDelete();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlDeleteAll();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmSynchronizeType> getSynchronize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCacheType getCache();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmFilterType> getFilter();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCascade();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmFetchStyleWithSubselectEnum getFetch();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmLazyWithExtraEnum getLazy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmOuterJoinEnum getOuterJoin();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getBatchSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isInverse();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOptimisticLock();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCollectionType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getPersister();

// todo : not available on all.  do we need a specific interface for these?
//	public String getSort();
//	public String getOrderBy();
}
