/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.internal;

import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;
import jakarta.xml.bind.ValidationEventLocator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ValidationEventHandler implementation providing easier access to where (line/column) an error occurred.
 *
 * @author Steve Ebersole
 */
public class ContextProvidingValidationEventHandler implements ValidationEventHandler {
	private int lineNumber;
	private int columnNumber;
	private String message;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean handleEvent(ValidationEvent validationEvent) {
		ValidationEventLocator locator = validationEvent.getLocator();
		lineNumber = locator.getLineNumber();
		columnNumber = locator.getColumnNumber();
		message = validationEvent.getMessage();
		return false;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getLineNumber() {
		return lineNumber;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getColumnNumber() {
		return columnNumber;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getMessage() {
		return message;
	}
}
