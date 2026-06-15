/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.spi;

import jakarta.persistence.AccessType;
import org.hibernate.boot.jaxb.Origin;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmNamedNativeQueryType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmNamedQueryType;
import org.hibernate.boot.jaxb.mapping.spi.JaxbCollectionUserTypeRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbCompositeUserTypeRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbConverterImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbConverterRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEmbeddableImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEmbeddableInstantiatorRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEntityImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbEntityMappingsImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbJavaTypeRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbJdbcTypeRegistrationImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbMappedSuperclassImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbNamedHqlQueryImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbNamedNativeQueryImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbNamedStoredProcedureQueryImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbUserTypeRegistrationImpl;

import java.util.List;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface XmlDocument {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Origin getOrigin();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbEntityMappingsImpl getRoot();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbEntityImpl> getEntityMappings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbMappedSuperclassImpl> getMappedSuperclassMappings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbEmbeddableImpl> getEmbeddableMappings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbConverterImpl> getConverters();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbConverterRegistrationImpl> getConverterRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbJavaTypeRegistrationImpl> getJavaTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbJdbcTypeRegistrationImpl> getJdbcTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbUserTypeRegistrationImpl> getUserTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbCompositeUserTypeRegistrationImpl> getCompositeUserTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbCollectionUserTypeRegistrationImpl> getCollectionUserTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbEmbeddableInstantiatorRegistrationImpl> getEmbeddableInstantiatorRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, JaxbNamedHqlQueryImpl> getJpaNamedQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, JaxbNamedNativeQueryImpl> getJpaNamedNativeQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, JaxbHbmNamedQueryType> getHibernateNamedQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, JaxbHbmNamedNativeQueryType> getHibernateNamedNativeQueries();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, JaxbNamedStoredProcedureQueryImpl> getNamedStoredProcedureQueries();

	interface Defaults {
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getPackage();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		AccessType getAccessType();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getAccessorStrategy();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getCatalog();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		String getSchema();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean isAutoImport();
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		boolean isLazinessImplied();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Defaults getDefaults();


}
