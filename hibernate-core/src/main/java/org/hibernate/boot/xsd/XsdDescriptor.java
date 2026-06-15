/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.xsd;

import javax.xml.validation.Schema;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Representation of a locally resolved XSD
 *
 * @author Steve Ebersole
 */
public final class XsdDescriptor {
	private final String localResourceName;
	private final String namespaceUri;
	private final String version;
	private final Schema schema;

	XsdDescriptor(String localResourceName, Schema schema, String version, String namespaceUri) {
		this.localResourceName = localResourceName;
		this.schema = schema;
		this.version = version;
		this.namespaceUri = namespaceUri;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getLocalResourceName() {
		return localResourceName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getNamespaceUri() {
		return namespaceUri;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getVersion() {
		return version;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Schema getSchema() {
		return schema;
	}
}
