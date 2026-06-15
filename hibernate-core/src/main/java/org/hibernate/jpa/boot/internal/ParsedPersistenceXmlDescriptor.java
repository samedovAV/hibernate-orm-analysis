/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.internal;

import jakarta.persistence.FetchType;
import jakarta.persistence.PersistenceUnitTransactionType;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import org.hibernate.boot.archive.internal.ArchiveHelper;
import org.hibernate.bytecode.enhance.spi.EnhancementContext;
import org.hibernate.bytecode.spi.ClassTransformer;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hibernate.jpa.internal.JpaLogger.JPA_LOGGER;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// [PersistenceUnitDescriptor] implementation describing the information
/// gleaned from a `<persistence-unit/>` element in a `persistence.xml` when
/// Hibernate itself parses the `persistence.xml` file.
///
/// @author Steve Ebersole
public class ParsedPersistenceXmlDescriptor implements PersistenceUnitDescriptor {
	private final URL persistenceUnitRootUrl;

	private String name;
	private String providerClassName;

	private boolean excludeUnlistedClasses;
	private FetchType defaultToOneFetchType;
	private boolean useQuotedIdentifiers;
	private final List<String> classes = new ArrayList<>();
	private final List<String> mappingFiles = new ArrayList<>();
	private final List<URL> jarFileUrls = new ArrayList<>();

	private PersistenceUnitTransactionType transactionType;
	private Object nonJtaDataSource;
	private Object jtaDataSource;

	private ValidationMode validationMode;
	private SharedCacheMode sharedCacheMode;

	private final Properties properties = new Properties();

	public ParsedPersistenceXmlDescriptor(URL persistenceUnitRootUrl) {
		this.persistenceUnitRootUrl = persistenceUnitRootUrl;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public URL getPersistenceUnitRootUrl() {
		return persistenceUnitRootUrl;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setName(String name) {
		this.name = name;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchType getDefaultToOneFetchType() {
		return defaultToOneFetchType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setDefaultToOneFetchType(FetchType defaultToOneFetchType) {
		this.defaultToOneFetchType = defaultToOneFetchType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getNonJtaDataSource() {
		return nonJtaDataSource;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setNonJtaDataSource(Object nonJtaDataSource) {
		this.nonJtaDataSource = nonJtaDataSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getJtaDataSource() {
		return jtaDataSource;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setJtaDataSource(Object jtaDataSource) {
		this.jtaDataSource = jtaDataSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getProviderClassName() {
		return providerClassName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistenceUnitTransactionType getPersistenceUnitTransactionType() {
		return transactionType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTransactionType(PersistenceUnitTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isUseQuotedIdentifiers() {
		return useQuotedIdentifiers;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setUseQuotedIdentifiers(boolean useQuotedIdentifiers) {
		this.useQuotedIdentifiers = useQuotedIdentifiers;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Properties getProperties() {
		return properties;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isExcludeUnlistedClasses() {
		return excludeUnlistedClasses;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
		this.excludeUnlistedClasses = excludeUnlistedClasses;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ValidationMode getValidationMode() {
		return validationMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setValidationMode(ValidationMode validationMode) {
		this.validationMode = validationMode;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setValidationMode(String validationMode) {
		setValidationMode( ValidationMode.valueOf( validationMode ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SharedCacheMode getSharedCacheMode() {
		return sharedCacheMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setSharedCacheMode(SharedCacheMode sharedCacheMode) {
		this.sharedCacheMode = sharedCacheMode;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void setSharedCacheMode(String sharedCacheMode) {
		setSharedCacheMode( SharedCacheMode.valueOf( sharedCacheMode ) );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getManagedClassNames() {
		return classes;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getAllClassNames() {
		return classes;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addClasses(String... classes) {
		addClasses( Arrays.asList( classes ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addClasses(List<String> classes) {
		this.classes.addAll( classes );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getMappingFileNames() {
		return mappingFiles;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void addMappingFiles(String... mappingFiles) {
		addMappingFiles( Arrays.asList( mappingFiles ) );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addMappingFiles(List<String> mappingFiles) {
		this.mappingFiles.addAll( mappingFiles );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<URL> getJarFileUrls() {
		return jarFileUrls;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addJarFileUrl(URL jarFileUrl) {
		jarFileUrls.add( jarFileUrl );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addJarFileRefs(List<String> jarFiles) {
		try (URLClassLoader urlClassLoader = new URLClassLoader( new URL[] {persistenceUnitRootUrl},
				Thread.currentThread().getContextClassLoader() )) {
			jarFiles.forEach(
					jarFile -> addJarFileUrl( ArchiveHelper.resolveJarFileReference( jarFile, urlClassLoader ) ) );
		}
		catch (IOException ignore) {
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassLoader getTempClassLoader() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isClassTransformerRegistrationDisabled() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ClassTransformer pushClassTransformer(EnhancementContext enhancementContext) {
		if ( JPA_LOGGER.isDebugEnabled() ) {
			JPA_LOGGER.pushingClassTransformerUnsupported( getName() );
		}
		return null;
	}
}
