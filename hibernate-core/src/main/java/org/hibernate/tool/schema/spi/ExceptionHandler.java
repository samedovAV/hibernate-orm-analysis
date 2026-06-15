/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for how CommandAcceptanceException errors should be handled (logged, ignored, etc).
 *
 * @author Steve Ebersole
 */
public interface ExceptionHandler {
	/**
	 * Handle the CommandAcceptanceException error
	 *
	 * @param exception The CommandAcceptanceException to handle
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void handleException(CommandAcceptanceException exception);
}
