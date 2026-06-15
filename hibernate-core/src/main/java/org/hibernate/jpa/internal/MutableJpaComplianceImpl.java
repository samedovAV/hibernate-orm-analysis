/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.internal;

import java.util.Map;

import org.hibernate.jpa.spi.JpaCompliance;
import org.hibernate.jpa.spi.MutableJpaCompliance;

import static org.hibernate.cfg.JpaComplianceSettings.JPAQL_STRICT_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_CACHING_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_CLOSED_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_ID_GENERATOR_GLOBAL_SCOPE_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_LOAD_BY_ID_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_ORDER_BY_MAPPING_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_PROXY_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_QUERY_COMPLIANCE;
import static org.hibernate.cfg.JpaComplianceSettings.JPA_TRANSACTION_COMPLIANCE;
import static org.hibernate.internal.util.config.ConfigurationHelper.getBoolean;
import static org.hibernate.internal.util.config.ConfigurationHelper.toBoolean;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class MutableJpaComplianceImpl implements MutableJpaCompliance {
	private boolean orderByMappingCompliance;
	private boolean proxyCompliance;
	private boolean generatorNameScopeCompliance;
	private boolean queryCompliance;
	private boolean transactionCompliance;
	private boolean closedCompliance;
	private boolean cachingCompliance;
	private boolean loadByIdCompliance;

	public MutableJpaComplianceImpl(Map<?,?> configurationSettings) {
		this( configurationSettings,
				getBoolean( JPA_COMPLIANCE, configurationSettings ) );
	}

	@SuppressWarnings("ConstantConditions")
	public MutableJpaComplianceImpl(Map<?,?> configurationSettings, boolean jpaByDefault) {
		final Object legacyQueryCompliance = configurationSettings.get( JPAQL_STRICT_COMPLIANCE );

		proxyCompliance = getBoolean(
				JPA_PROXY_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		generatorNameScopeCompliance = getBoolean(
				JPA_ID_GENERATOR_GLOBAL_SCOPE_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		orderByMappingCompliance = getBoolean(
				JPA_ORDER_BY_MAPPING_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		queryCompliance = getBoolean(
				JPA_QUERY_COMPLIANCE,
				configurationSettings,
				toBoolean( legacyQueryCompliance, jpaByDefault )
		);
		transactionCompliance = getBoolean(
				JPA_TRANSACTION_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		closedCompliance = getBoolean(
				JPA_CLOSED_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		cachingCompliance = getBoolean(
				JPA_CACHING_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
		loadByIdCompliance = getBoolean(
				JPA_LOAD_BY_ID_COMPLIANCE,
				configurationSettings,
				jpaByDefault
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaQueryComplianceEnabled() {
		return queryCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaTransactionComplianceEnabled() {
		return transactionCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaClosedComplianceEnabled() {
		return closedCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaProxyComplianceEnabled() {
		return proxyCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaCacheComplianceEnabled() {
		return cachingCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isGlobalGeneratorScopeEnabled() {
		return generatorNameScopeCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isJpaOrderByMappingComplianceEnabled() {
		return orderByMappingCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isLoadByIdComplianceEnabled() {
		return loadByIdCompliance;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Mutators

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setOrderByMappingCompliance(boolean orderByMappingCompliance) {
		this.orderByMappingCompliance = orderByMappingCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setProxyCompliance(boolean proxyCompliance) {
		this.proxyCompliance = proxyCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setGeneratorNameScopeCompliance(boolean enabled) {
		this.generatorNameScopeCompliance = enabled;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setQueryCompliance(boolean queryCompliance) {
		this.queryCompliance = queryCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTransactionCompliance(boolean transactionCompliance) {
		this.transactionCompliance = transactionCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setClosedCompliance(boolean closedCompliance) {
		this.closedCompliance = closedCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCachingCompliance(boolean cachingCompliance) {
		this.cachingCompliance = cachingCompliance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setLoadByIdCompliance(boolean enabled) {
		this.loadByIdCompliance = enabled;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JpaCompliance immutableCopy() {
		return new JpaComplianceImpl.JpaComplianceBuilder()
				.setProxyCompliance( proxyCompliance )
				.setOrderByMappingCompliance( orderByMappingCompliance )
				.setGlobalGeneratorNameCompliance( generatorNameScopeCompliance )
				.setQueryCompliance( queryCompliance )
				.setTransactionCompliance( transactionCompliance )
				.setClosedCompliance( closedCompliance )
				.setCachingCompliance( cachingCompliance )
				.setLoadByIdCompliance( loadByIdCompliance )
				.createJpaCompliance();
	}
}
