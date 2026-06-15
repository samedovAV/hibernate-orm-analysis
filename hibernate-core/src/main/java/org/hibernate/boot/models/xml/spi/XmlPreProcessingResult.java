/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.spi;

import org.hibernate.boot.jaxb.mapping.spi.JaxbManagedType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Result of {@linkplain XmlPreProcessor#preProcessXmlResources}
 *
 * @author Steve Ebersole
 */
public interface XmlPreProcessingResult {

	/**
	 * All XML documents (JAXB roots)
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<XmlDocument> getDocuments();

	/**
	 * All classes named across all XML mappings
	 *
	 * @see JaxbManagedType#getClazz()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getMappedClasses();

	/**
	 * All "type names" named across all XML mappings.
	 *
	 * @apiNote This accounts for dynamic models
	 *
	 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbEntityImpl#getName()
	 * @see org.hibernate.boot.jaxb.mapping.spi.JaxbEmbeddableImpl#getName()
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getMappedNames();
}
