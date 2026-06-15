/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.annotations;

import java.util.List;
import java.util.Set;

import org.hibernate.boot.internal.RootMappingDefaults;
import org.hibernate.boot.models.spi.ConversionRegistration;
import org.hibernate.boot.models.spi.ConverterRegistration;
import org.hibernate.boot.models.spi.GlobalRegistrations;
import org.hibernate.boot.models.xml.spi.PersistenceUnitMetadata;
import org.hibernate.models.spi.ClassDetailsRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class DomainModelSource {
	private final ClassDetailsRegistry classDetailsRegistry;
	private final GlobalRegistrations globalRegistrations;
	private final RootMappingDefaults effectiveMappingDefaults;
	private final PersistenceUnitMetadata persistenceUnitMetadata;
	private final List<String> allKnownClassNames;

	public DomainModelSource(
			ClassDetailsRegistry classDetailsRegistry,
			List<String> allKnownClassNames,
			GlobalRegistrations globalRegistrations,
			RootMappingDefaults effectiveMappingDefaults,
			PersistenceUnitMetadata persistenceUnitMetadata) {
		this.classDetailsRegistry = classDetailsRegistry;
		this.allKnownClassNames = allKnownClassNames;
		this.globalRegistrations = globalRegistrations;
		this.effectiveMappingDefaults = effectiveMappingDefaults;
		this.persistenceUnitMetadata = persistenceUnitMetadata;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassDetailsRegistry getClassDetailsRegistry() {
		return classDetailsRegistry;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GlobalRegistrations getGlobalRegistrations() {
		return globalRegistrations;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RootMappingDefaults getEffectiveMappingDefaults() {
		return effectiveMappingDefaults;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceUnitMetadata getPersistenceUnitMetadata() {
		return persistenceUnitMetadata;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<ConversionRegistration> getConversionRegistrations() {
		return globalRegistrations.getConverterRegistrations();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<ConverterRegistration> getConverterRegistrations() {
		return globalRegistrations.getJpaConverters();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getManagedClassNames() {
		return allKnownClassNames;
	}
}
