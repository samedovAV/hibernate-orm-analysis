/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.internal.stax;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;

import org.hibernate.boot.xsd.ConfigXsdSupport;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class ConfigurationEventReader extends AbstractEventReader {
	private static final String ROOT_ELEMENT_NAME = "persistence";

	public ConfigurationEventReader(XMLEventReader reader, XMLEventFactory xmlEventFactory) {
		super(
				ROOT_ELEMENT_NAME,
				ConfigXsdSupport.configurationXsd(),
				reader,
				xmlEventFactory
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean shouldBeMappedToLatestJpaDescriptor(String uri) {
		return !ConfigXsdSupport.configurationXsd().getNamespaceUri().equals( uri );
	}
}
