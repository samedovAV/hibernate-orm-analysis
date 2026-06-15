/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.Incubating;
import org.hibernate.audit.AuditStrategy;
import org.hibernate.boot.model.TypeDefinitionRegistry;
import org.hibernate.boot.model.naming.ObjectNameNormalizer;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.temporal.TemporalTableStrategy;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;

import static org.hibernate.cfg.MappingSettings.JAVA_TIME_USE_DIRECT_JDBC;
import static org.hibernate.cfg.MappingSettings.PREFER_LOCALE_LANGUAGE_TAG;
import static org.hibernate.cfg.MappingSettings.PREFER_NATIVE_ENUM_TYPES;
import static org.hibernate.audit.AuditStrategy.DEFAULT;
import static org.hibernate.temporal.TemporalTableStrategy.AUTO;
import static org.hibernate.internal.util.config.ConfigurationHelper.getBoolean;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes the context in which the process of building {@link org.hibernate.boot.Metadata}
 * from {@link org.hibernate.boot.MetadataSources} occurs.
 * <p>
 * {@link MetadataBuildingContext}s are hierarchical: global, persistence unit, document, mapping.
 *
 * @author Steve Ebersole
 *
 * @since 5.0
 */
public interface MetadataBuildingContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BootstrapContext getBootstrapContext();

	/**
	 * Access to the options specified by the {@link org.hibernate.boot.MetadataBuilder}
	 *
	 * @return The options
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MetadataBuildingOptions getBuildingOptions();

	/**
	 * Access to mapping defaults in effect for this context
	 *
	 * @return The mapping defaults.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EffectiveMappingDefaults getEffectiveDefaults();

	/**
	 * Access to the collector of metadata as we build it.
	 *
	 * @return The metadata collector.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InFlightMetadataCollector getMetadataCollector();

	/**
	 * Not sure how I feel about this exposed here
	 *
	 * @return The ObjectNameNormalizer
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ObjectNameNormalizer getObjectNameNormalizer();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private StandardServiceRegistry getRegistry() {
		return getBootstrapContext().getServiceRegistry();
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getPreferredSqlTypeCodeForBoolean() {
		return ConfigurationHelper.getPreferredSqlTypeCodeForBoolean( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getPreferredSqlTypeCodeForDuration() {
		return ConfigurationHelper.getPreferredSqlTypeCodeForDuration( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getPreferredSqlTypeCodeForUuid() {
		return ConfigurationHelper.getPreferredSqlTypeCodeForUuid( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getPreferredSqlTypeCodeForInstant() {
		return ConfigurationHelper.getPreferredSqlTypeCodeForInstant( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default int getPreferredSqlTypeCodeForArray() {
		return ConfigurationHelper.getPreferredSqlTypeCodeForArray( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isPreferJavaTimeJdbcTypesEnabled() {
		return isPreferJavaTimeJdbcTypesEnabled( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isPreferNativeEnumTypesEnabled() {
		return isPreferNativeEnumTypesEnabled( getRegistry() );
	}

	@Incubating
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default boolean isPreferLocaleLanguageTagEnabled() {
		return isPreferLocaleLanguageTagEnabled( getRegistry() );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static boolean isPreferJavaTimeJdbcTypesEnabled(ServiceRegistry serviceRegistry) {
		return isPreferJavaTimeJdbcTypesEnabled( serviceRegistry.requireService( ConfigurationService.class ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static boolean isPreferNativeEnumTypesEnabled(ServiceRegistry serviceRegistry) {
		return isPreferNativeEnumTypesEnabled( serviceRegistry.requireService( ConfigurationService.class ) );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	static boolean isPreferLocaleLanguageTagEnabled(ServiceRegistry serviceRegistry) {
		return isPreferLocaleLanguageTagEnabled( serviceRegistry.requireService( ConfigurationService.class ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static boolean isPreferJavaTimeJdbcTypesEnabled(ConfigurationService configurationService) {
		return getBoolean( JAVA_TIME_USE_DIRECT_JDBC, configurationService.getSettings() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static boolean isPreferNativeEnumTypesEnabled(ConfigurationService configurationService) {
		//TODO: HHH-17905 proposes to switch this default to true
		return getBoolean( PREFER_NATIVE_ENUM_TYPES, configurationService.getSettings() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	static boolean isPreferLocaleLanguageTagEnabled(ConfigurationService configurationService) {
		return getBoolean( PREFER_LOCALE_LANGUAGE_TAG, configurationService.getSettings() );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TypeDefinitionRegistry getTypeDefinitionRegistry();

	/**
	 * The name of the contributor whose mappings we are currently processing
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCurrentContributorName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default TemporalTableStrategy getTemporalTableStrategy() {
		return AUTO;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default AuditStrategy getAuditStrategy() {
		return DEFAULT;
	}
}
