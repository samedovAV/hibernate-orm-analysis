/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.hibernate.Incubating;
import org.hibernate.MappingException;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.MappedSuperclass;
import org.hibernate.metamodel.mapping.DiscriminatorType;
import org.hibernate.query.named.NamedObjectRepository;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * The SPI-level {@link Metadata} contract.
 *
 * @author Steve Ebersole
 *
 * @since 5.0
 */
public interface MetadataImplementor extends Metadata {
	/**
	 * Access to the options used to build this {@code Metadata}
	 *
	 * @return The {@link MetadataBuildingOptions}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataBuildingOptions getMetadataBuildingOptions();

	/**
	 * Access to the {@link TypeConfiguration} belonging to the {@link BootstrapContext}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeConfiguration getTypeConfiguration();

	/**
	 * Access to the {@link SqmFunctionRegistry} belonging to the {@link BootstrapContext}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmFunctionRegistry getFunctionRegistry();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedObjectRepository buildNamedQueryRepository();

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void orderColumns(boolean forceOrdering);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void validate() throws MappingException;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<MappedSuperclass> getMappedSuperclassMappingsCopy();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void initSessionFactory(SessionFactoryImplementor sessionFactoryImplementor);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitRegisteredComponents(Consumer<Component> consumer);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Component getGenericComponent(Class<?> componentClass);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	DiscriminatorType<?> resolveEmbeddableDiscriminatorType(Class<?> embeddableClass, Supplier<DiscriminatorType<?>> supplier);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor buildSessionFactory();
}
