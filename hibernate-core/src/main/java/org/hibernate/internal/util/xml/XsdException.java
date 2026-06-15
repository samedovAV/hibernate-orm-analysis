/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.internal.util.xml;

import org.hibernate.HibernateException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Indicates an issue finding or loading an XSD schema.
 *
 * @author Steve Ebersole
 */
public class XsdException extends HibernateException {
	private final String xsdName;

	public XsdException(String message, String xsdName) {
		super( message );
		this.xsdName = xsdName;
	}

	public XsdException(String message, Throwable root, String xsdName) {
		super( message, root );
		this.xsdName = xsdName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXsdName() {
		return xsdName;
	}
}
