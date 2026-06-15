/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.generator;

import java.util.Properties;

import org.hibernate.Incubating;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Value;
import org.hibernate.models.spi.MemberDetails;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import static org.hibernate.boot.model.relational.internal.SqlStringGenerationContextImpl.fromExplicit;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information useful during {@linkplain Generator} creation and initialization.
 *
 * @see AnnotationBasedGenerator
 * @see org.hibernate.id.Configurable#configure(GeneratorCreationContext, Properties)
 *
 * @since 6.2
 */
@Incubating
public interface GeneratorCreationContext {
	/**
	 * View of the relational database objects (tables, sequences, etc.)
	 * and namespaces (catalogs and schemas). Generators may add new
	 * tables or sequences to the returned {@link Database}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Database getDatabase();

	/**
	 * Access to available services.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ServiceRegistry getServiceRegistry();

	/**
	 * The default catalog name, if one.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultCatalog();

	/**
	 * The default schema name, if one.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDefaultSchema();

	/**
	 * Mapping details for the entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistentClass getPersistentClass();

	/**
	 * Mapping details for the root of the {@linkplain #getPersistentClass() entity} hierarchy.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootClass getRootClass();

	/**
	 * The entity identifier or id-bag property details.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Property getProperty();

	/**
	 * The identifier.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Value getValue();

	/**
	 * Mapping details for the identifier type.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default Type getType() {
		return getProperty().getType();
	}

	/**
	 * The {@link SqlStringGenerationContext} to use when generating SQL.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SqlStringGenerationContext getSqlStringGenerationContext() {
		final var database = getDatabase();
		return fromExplicit( database.getJdbcEnvironment(), database, getDefaultCatalog(), getDefaultSchema() );
	}

	/**
	 * Access to the {@link MemberDetails}.
	 *
	 * @since 7.3
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default MemberDetails getMemberDetails() {
		return null;
	}

}
