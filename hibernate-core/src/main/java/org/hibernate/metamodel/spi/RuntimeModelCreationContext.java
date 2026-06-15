/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.spi;

import org.hibernate.action.queue.spi.PlanningOptions;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.spi.CacheImplementor;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.generator.Generator;
import org.hibernate.mapping.GeneratorSettings;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.java.spi.JavaTypeRegistry;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface RuntimeModelCreationContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getSessionFactory();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BootstrapContext getBootstrapContext();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataImplementor getBootModel();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingMetamodelImplementor getDomainModel();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PlanningOptions getGraphPlanningOptions();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JavaTypeRegistry getJavaTypeRegistry() {
		return getTypeConfiguration().getJavaTypeRegistry();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MetadataImplementor getMetadata() {
		return getBootModel();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunctionRegistry getFunctionRegistry();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, Object> getSettings();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Dialect getDialect();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CacheImplementor getCache();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryOptions getSessionFactoryOptions();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcServices getJdbcServices();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqlStringGenerationContext getSqlStringGenerationContext();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, Generator> getGenerators();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	GeneratorSettings getGeneratorSettings();

	// For Hibernate Reactive
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Generator getOrCreateIdGenerator(String rootName, PersistentClass persistentClass);
}
