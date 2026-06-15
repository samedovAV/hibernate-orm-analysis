/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.internal;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import jakarta.persistence.FetchType;
import org.hibernate.bytecode.enhance.spi.EnhancementContext;
import org.hibernate.bytecode.spi.ClassTransformer;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.hibernate.jpa.internal.TransformerTracker;
import org.hibernate.jpa.internal.TransformerTracker.TransformerKey;
import org.hibernate.jpa.internal.enhance.EnhancingClassTransformerImpl;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.PersistenceUnitTransactionType;


import static org.hibernate.jpa.internal.JpaLogger.JPA_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Wraps a JPA {@linkplain PersistenceUnitInfo} as Hibernate's {@linkplain PersistenceUnitDescriptor}
///
/// @author Steve Ebersole
public class PersistenceUnitInfoDescriptor implements PersistenceUnitDescriptor {
	private final PersistenceUnitInfo persistenceUnitInfo;
	private final boolean disableClassTransformerRegistration;
	private ClassTransformer classTransformer;

	public PersistenceUnitInfoDescriptor(PersistenceUnitInfo persistenceUnitInfo) {
		this( persistenceUnitInfo, false );
	}

	public PersistenceUnitInfoDescriptor(PersistenceUnitInfo persistenceUnitInfo, boolean disableClassTransformerRegistration) {
		this.persistenceUnitInfo = persistenceUnitInfo;
		this.disableClassTransformerRegistration = disableClassTransformerRegistration;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public URL getPersistenceUnitRootUrl() {
		return persistenceUnitInfo.getPersistenceUnitRootUrl();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return persistenceUnitInfo.getPersistenceUnitName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getNonJtaDataSource() {
		return persistenceUnitInfo.getNonJtaDataSource();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getJtaDataSource() {
		return persistenceUnitInfo.getJtaDataSource();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getProviderClassName() {
		return persistenceUnitInfo.getPersistenceProviderClassName();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceUnitTransactionType getPersistenceUnitTransactionType() {
		return persistenceUnitInfo.getTransactionType();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUseQuotedIdentifiers() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Properties getProperties() {
		return persistenceUnitInfo.getProperties();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ClassLoader getClassLoader() {
		return persistenceUnitInfo.getClassLoader();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassLoader getTempClassLoader() {
		return persistenceUnitInfo.getNewTempClassLoader();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isExcludeUnlistedClasses() {
		return persistenceUnitInfo.excludeUnlistedClasses();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public FetchType getDefaultToOneFetchType() {
		return persistenceUnitInfo.getDefaultToOneFetchType();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public ValidationMode getValidationMode() {
		return persistenceUnitInfo.getValidationMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public SharedCacheMode getSharedCacheMode() {
		return persistenceUnitInfo.getSharedCacheMode();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<String> getManagedClassNames() {
		return persistenceUnitInfo.getManagedClassNames();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<String> getAllClassNames() {
		return persistenceUnitInfo.getAllClassNames();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<String> getMappingFileNames() {
		return persistenceUnitInfo.getMappingFileNames();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public List<URL> getJarFileUrls() {
		return persistenceUnitInfo.getJarFileUrls();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isClassTransformerRegistrationDisabled() {
		return disableClassTransformerRegistration;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassTransformer pushClassTransformer(EnhancementContext enhancementContext) {
		if ( this.classTransformer != null ) {
			throw new PersistenceException(
					"Persistence unit ["
					+ persistenceUnitInfo.getPersistenceUnitName()
					+ "] can only have a single class transformer."
			);
		}

		final TransformerKey transformerKey = TransformerKey.from( persistenceUnitInfo );
		if ( !TransformerTracker.canSupplyTransformer( transformerKey ) ) {
			// transformer was already registered from `jakarta.persistence.spi.PersistenceProvider#getClassTransformer`
			// just skip
			if ( JPA_LOGGER.isTraceEnabled() ) {
				JPA_LOGGER.duplicatedRequestForClassTransformer( transformerKey.puName(), transformerKey.loaderName() );
			}
			// EARLY EXIT!!!
			return null;
		}

		// During testing, we will return a null temp class loader
		// in cases where we don't care about enhancement
		if ( persistenceUnitInfo.getNewTempClassLoader() != null ) {
			if ( JPA_LOGGER.isTraceEnabled() ) {
				JPA_LOGGER.pushingClassTransformers( getName(), String.valueOf( enhancementContext.getLoadingClassLoader() ) );
			}
			final EnhancingClassTransformerImpl classTransformer =
					new EnhancingClassTransformerImpl( enhancementContext );
			this.classTransformer = classTransformer;
			persistenceUnitInfo.addTransformer( classTransformer );
		}

		return classTransformer;
	}
}
