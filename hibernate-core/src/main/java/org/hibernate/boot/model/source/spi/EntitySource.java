/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.List;
import java.util.Map;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmNamedNativeQueryType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmNamedQueryType;
import org.hibernate.boot.model.CustomSql;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract describing source of information related to mapping an entity.
 *
 * @author Steve Ebersole
 */
public interface EntitySource extends IdentifiableTypeSource, ToolingHintContextContainer, EntityNamingSourceContributor {
	/**
	 * Obtain the primary table for this entity.
	 *
	 * @return The primary table.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableSpecificationSource getPrimaryTable();

	/**
	 * Obtain the secondary tables for this entity
	 *
	 * @return returns an iterator over the secondary tables for this entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String,SecondaryTableSource> getSecondaryTableMap();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getXmlNodeName();

	/**
	 * Obtain the name of a custom persister class to be used.
	 *
	 * @return The custom persister class name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomPersisterClassName();

	/**
	 * Is this entity lazy (proxyable)?
	 *
	 * @return {@code true} indicates the entity is lazy; {@code false} non-lazy.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isLazy();

	/**
	 * For {@linkplain #isLazy() lazy} entities, obtain the interface to use in constructing its proxies.
	 *
	 * @return The proxy interface name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getProxy();

	/**
	 * Obtain the batch-size to be applied when initializing proxies of this entity.
	 *
	 * @return returns the batch-size.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getBatchSize();

	/**
	 * Is the entity abstract?
	 * <p>
	 * The implication is whether the entity maps to a database table.
	 *
	 * @return {@code true} indicates the entity is abstract; {@code false} non-abstract; {@code null}
	 * indicates that a reflection check should be done when building the persister.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isAbstract();

	/**
	 * Did the source specify dynamic inserts?
	 *
	 * @return {@code true} indicates dynamic inserts will be used; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDynamicInsert();

	/**
	 * Did the source specify dynamic updates?
	 *
	 * @return {@code true} indicates dynamic updates will be used; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isDynamicUpdate();

	/**
	 * Did the source specify to perform selects to decide whether to perform (detached) updates?
	 *
	 * @return {@code true} indicates selects will be done; {@code false} otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSelectBeforeUpdate();

	/**
	 * Obtain the name of a named-query that will be used for loading this entity
	 *
	 * @return THe custom loader query name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCustomLoaderName();

	/**
	 * Obtain the custom SQL to be used for inserts for this entity
	 *
	 * @return The custom insert SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlInsert();

	/**
	 * Obtain the custom SQL to be used for updates for this entity
	 *
	 * @return The custom update SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlUpdate();

	/**
	 * Obtain the custom SQL to be used for deletes for this entity
	 *
	 * @return The custom delete SQL
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CustomSql getCustomSqlDelete();

	/**
	 * Obtain any additional table names on which to synchronize (auto flushing) this entity.
	 *
	 * @return Additional synchronized table names or 0 sized String array, never return null.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String[] getSynchronizedTableNames();

	/**
	 * Get the actual discriminator value in case of a single table inheritance
	 *
	 * @return the actual discriminator value in case of a single table inheritance or {@code null} in case there is no
	 *         explicit value or a different inheritance scheme
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDiscriminatorMatchValue();

	/**
	 * Obtain the filters for this entity.
	 *
	 * @return returns an array of the filters for this entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FilterSource[] getFilterSources();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmNamedQueryType> getNamedQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbHbmNamedNativeQueryType> getNamedNativeQueries();
}
