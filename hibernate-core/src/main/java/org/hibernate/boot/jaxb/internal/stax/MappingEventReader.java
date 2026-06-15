/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.internal.stax;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;

import org.hibernate.boot.xsd.MappingXsdSupport;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * StAX EVentReader for reading `mapping.xml` streams
 *
 * @author Steve Ebersole
 * @author Hardy Ferentschik
 */
public class MappingEventReader extends AbstractEventReader {
	private static final String ROOT_ELEMENT_NAME = "entity-mappings";

	public MappingEventReader(XMLEventReader reader, XMLEventFactory xmlEventFactory) {
		super( ROOT_ELEMENT_NAME, MappingXsdSupport.latestDescriptor(), reader, xmlEventFactory );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected boolean shouldBeMappedToLatestJpaDescriptor(String uri) {
		return !MappingXsdSupport.latestDescriptor().getNamespaceUri().equals( uri );
	}
}
