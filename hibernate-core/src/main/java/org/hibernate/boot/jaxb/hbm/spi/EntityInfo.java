/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Common interface for all entity mappings (root entity and sub-entity mappings).
 *
 * @author Steve Ebersole
 */
public interface EntityInfo extends ToolingHintContainer, ResultSetMappingContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getProxy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isAbstract();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isLazy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getBatchSize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDynamicInsert();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDynamicUpdate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSelectBeforeUpdate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmTuplizerType> getTuplizer();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getPersister();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmLoaderType getLoader();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlInsert();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlUpdate();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmCustomSqlDmlType getSqlDelete();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmSynchronizeType> getSynchronize();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmFetchProfileType> getFetchProfile();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmResultSetMappingType> getResultset();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmNamedNativeQueryType> getSqlQuery();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmNamedQueryType> getQuery();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List getAttributes();
}
