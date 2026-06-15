/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.jpa.boot.spi;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import jakarta.persistence.FetchType;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.PersistenceUnitTransactionType;

import org.hibernate.bytecode.enhance.spi.EnhancementContext;
import org.hibernate.bytecode.spi.ClassTransformer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Abstraction for dealing with `<persistence-unit/>` information
/// specified in the `persistence.xml` file.  This information can
/// come from either:
///
///   - from the Jakarta EE container as an instance of
///     [jakarta.persistence.spi.PersistenceUnitInfo]
///   - in an SE environment, parsed by Hibernate itself
///   - [org.hibernate.jpa.HibernatePersistenceConfiguration]
///
/// @see jakarta.persistence.spi.PersistenceUnitInfo
/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl
///
/// @author Steve Ebersole
public interface PersistenceUnitDescriptor {

	/// The persistence unit name.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getPersistenceUnitName()
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getName
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/// The root url for the persistence unit.
	///
	/// @implNote When Hibernate performs scanning, this URL is used as the base for scanning.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	URL getPersistenceUnitRootUrl();

	/// The explicitly specified provider class name, or `null` if not specified.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getPersistenceProviderClassName
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getProvider
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getProviderClassName();

	/// Whether scanning for classes should be performed.  If not, the list of classes available is limited to:
	///   - classes listed in [#getManagedClassNames()]
	///   - classes named in all [#getMappingFileNames]
	///   - classes discovered in [#getJarFileUrls]
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#excludeUnlistedClasses
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#isExcludeUnlistedClasses
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isExcludeUnlistedClasses();

	/// Default fetching to be applied to to-one mappings when {@linkplain FetchType#DEFAULT none} is specified.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getDefaultToOneFetchType()
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getDefaultToOneFetchType
	/// @see jakarta.persistence.OneToOne#fetch()
	/// @see jakarta.persistence.ManyToOne#fetch()
	///
	/// @since 8.0
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	FetchType getDefaultToOneFetchType();

	/// Whether the use of identifier quoting is in effect for this whole persistence unit.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUseQuotedIdentifiers();

	/// Names of classes explicitly listed in the persistence-unit.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getManagedClassNames
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getClasses
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getManagedClassNames();

	/// Names of all classes in the persistence-unit - explicitly listed, listed in mapping XML and discovered.
	///
	/// @apiNote This is not supported for all implementors / scenarios.  In such cases, it simply returns {@link #getManagedClassNames()}.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getAllClassNames()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getAllClassNames();

	/// Names of mapping-files explicitly listed in the persistence-unit.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getMappingFileNames
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getMappingFiles
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getMappingFileNames();

	/// Names of JAR files explicitly listed in the persistence-unit.
	///
	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getJarFileUrls
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getJarFiles
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<URL> getJarFileUrls();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getTransactionType()
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getTransactionType
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PersistenceUnitTransactionType getPersistenceUnitTransactionType();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getNonJtaDataSource
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getNonJtaDataSource
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getNonJtaDataSource();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getJtaDataSource
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getJtaDataSource
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getJtaDataSource();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getValidationMode
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getValidationMode
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ValidationMode getValidationMode();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getSharedCacheMode
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getSharedCacheMode
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SharedCacheMode getSharedCacheMode();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getProperties
	/// @see org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl.JaxbPersistenceUnitImpl#getPropertyContainer
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Properties getProperties();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getClassLoader
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoader getClassLoader();

	/// @see jakarta.persistence.spi.PersistenceUnitInfo#getNewTempClassLoader
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassLoader getTempClassLoader();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isClassTransformerRegistrationDisabled();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ClassTransformer pushClassTransformer(EnhancementContext enhancementContext);
}
