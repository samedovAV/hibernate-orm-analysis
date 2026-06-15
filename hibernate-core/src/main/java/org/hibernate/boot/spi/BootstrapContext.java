/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Incubating;
import org.hibernate.boot.CacheRegionDefinition;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;
import org.hibernate.boot.model.convert.spi.ConverterDescriptor;
import org.hibernate.boot.model.relational.AuxiliaryDatabaseObject;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.scan.spi.Scanner;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.jpa.spi.MutableJpaCompliance;
import org.hibernate.metamodel.spi.ManagedTypeRepresentationResolver;
import org.hibernate.models.spi.ModelsContext;
import org.hibernate.query.sqm.function.SqmFunctionDescriptor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.resource.beans.spi.BeanInstanceProducer;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines a context for things available during the process of bootstrapping
 * a {@link org.hibernate.SessionFactory} which are expected to be cleaned up
 * after the {@code SessionFactory} is built.
 *
 * @author Steve Ebersole
 */
@Incubating
public interface BootstrapContext {
	/**
	 * The service registry available to bootstrapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	StandardServiceRegistry getServiceRegistry();

	/**
	 * In-flight form of {@link org.hibernate.jpa.spi.JpaCompliance}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MutableJpaCompliance getJpaCompliance();

	/**
	 * The {@link TypeConfiguration} belonging to this {@code BootstrapContext}.
	 *
	 * @see TypeConfiguration
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	/**
	 * Access to the {@code hibernate-models} {@linkplain ModelsContext}
	 */
	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelsContext getModelsContext();

	/**
	 * The {@link SqmFunctionRegistry} belonging to this {@code BootstrapContext}.
	 *
	 * @see SqmFunctionRegistry
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunctionRegistry getFunctionRegistry();

	/**
	 * The {@link BeanInstanceProducer} to use when creating custom type references.
	 *
	 * @implNote Usually a {@link org.hibernate.boot.internal.TypeBeanInstanceProducer}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BeanInstanceProducer getCustomTypeProducer();

	/**
	 * Options specific to building the {@linkplain Metadata boot metamodel}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataBuildingOptions getMetadataBuildingOptions();

	/**
	 * Access to the {@link ClassLoaderService}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoaderService getClassLoaderService();

	/**
	 * Access to the {@link ManagedBeanRegistry}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedBeanRegistry getManagedBeanRegistry();

	/**
	 * Access to the {@link ConfigurationService}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ConfigurationService getConfigurationService();

	/**
	 * Whether the bootstrap was initiated from JPA bootstrapping.
	 *
	 * @implSpec This is used
	 *
	 * @see #markAsJpaBootstrap()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isJpaBootstrap();

	/**
	 * Indicates that bootstrap was initiated from JPA bootstrapping.
	 *
	 * @implSpec Internally, {@code false} is the assumed value.
	 *           We only need to call this to mark it {@code true}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void markAsJpaBootstrap();

	/**
	 * Access the temporary {@link ClassLoader} passed to us, as defined by
	 * {@link jakarta.persistence.spi.PersistenceUnitInfo#getNewTempClassLoader()},
	 * if any.
	 *
	 * @return The temporary {@code ClassLoader}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoader getJpaTempClassLoader();

	/**
	 * Access to class loading capabilities.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoaderAccess getClassLoaderAccess();

	/**
	 * Access to the {@link ArchiveDescriptorFactory} used for scanning.
	 *
	 * @return The {@link ArchiveDescriptorFactory}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ArchiveDescriptorFactory getArchiveDescriptorFactory();

	/**
	 * Access to the {@link Scanner} to be used
	 * for scanning.
	 * <p>
	 * Can be:
	 * <ul>
	 *     <li>An instance of {@link Scanner},
	 *     <li>a {@code Class} reference to the {@code Scanner} implementor, or
	 *     <li>a string naming the {@code Scanner} implementor.
	 * </ul>
	 *
	 * @return The scanner
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getScanning();

	/**
	 * Access to the Jandex index passed by call to
	 * {@link org.hibernate.boot.MetadataBuilder#applyIndexView(Object)}, if any.
	 *
	 * @return The Jandex index
	 *
	 * @deprecated Set via the {@code hibernate-models} setting {@code hibernate.models.jandex.index} instead
	 */
	@Deprecated
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getJandexView();

	/**
	 * Access to any SQL functions explicitly registered with the
	 * {@link org.hibernate.boot.MetadataBuilder}.
	 * This does not include {@code Dialect}-registered functions.
	 * <p>
	 * Should never return {@code null}.
	 *
	 * @return The {@link SqmFunctionDescriptor}s registered via {@code MetadataBuilder}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, SqmFunctionDescriptor> getSqlFunctions();

	/**
	 * Access to any {@link AuxiliaryDatabaseObject}s explicitly registered with
	 * the {@link org.hibernate.boot.MetadataBuilder}.
	 * This does not include {@link AuxiliaryDatabaseObject}s defined in mappings.
	 * <p>
	 * Should never return {@code null}.
	 *
	 * @return The {@link AuxiliaryDatabaseObject}s registered via {@code MetadataBuilder}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<AuxiliaryDatabaseObject> getAuxiliaryDatabaseObjectList();

	/**
	 * Access to collected {@link jakarta.persistence.AttributeConverter} definitions.
	 * <p>
	 * Should never return {@code null}.
	 *
	 * @return The {@link ConverterDescriptor}s registered via {@code MetadataBuilder}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<ConverterDescriptor<?, ?>> getAttributeConverters();

	/**
	 * Access to all explicit cache region mappings.
	 * <p>
	 * Should never return {@code null}.
	 *
	 * @return Explicit cache region mappings
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Collection<CacheRegionDefinition> getCacheRegionDefinitions();

	/**
	 * @see ManagedTypeRepresentationResolver
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ManagedTypeRepresentationResolver getRepresentationStrategySelector();

	/**
	 * Releases the "bootstrap only" resources held by this {@code BootstrapContext}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();

	/**
	 * To support Envers.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerAdHocBasicType(BasicType<?> basicType);

	/**
	 * To support Envers.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> BasicType<T> resolveAdHocBasicType(String key);

	/**
	 * Find a previously registered ad-hoc BasicTypeImpl based on java and jdbc type.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> BasicType<T> findAdHocBasicType(JavaType<T> javaType, JdbcType jdbcType);
}
