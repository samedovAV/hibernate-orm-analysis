/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.type.format.jaxb;

import org.hibernate.type.format.FormatMapper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class JaxbIntegration {

	private static final boolean JAXB_XML_AVAILABLE = ableToLoadJakartaJaxb();

	private static final JaxbXmlFormatMapper XML_FORMAT_MAPPER =
			JAXB_XML_AVAILABLE ? new JaxbXmlFormatMapper( false ) : null;
	private static final JaxbXmlFormatMapper LEGACY_XML_FORMAT_MAPPER =
			JAXB_XML_AVAILABLE ? new JaxbXmlFormatMapper( true ) : null;

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	private static boolean ableToLoadJakartaJaxb() {
		try {
			//N.B. intentionally not using the context classloader
			// as we're storing these in static references;
			// IMO it's reasonable to expect that such dependencies are made reachable from the ORM classloader.
			// (we can change this if it's more problematic than expected).
			JaxbIntegration.class.getClassLoader().loadClass( "jakarta.xml.bind.JAXBContext" );
			return true;
		}
		catch (ClassNotFoundException | LinkageError e) {
			return false;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static FormatMapper getJaxbXmlFormatMapperOrNull() {
		return XML_FORMAT_MAPPER;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static FormatMapper getJaxbLegacyXmlFormatMapperOrNull() {
		return LEGACY_XML_FORMAT_MAPPER;
	}

}
