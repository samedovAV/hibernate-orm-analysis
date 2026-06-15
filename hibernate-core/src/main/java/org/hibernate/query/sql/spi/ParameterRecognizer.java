/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sql.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Defines the "callback" process of recognizing native query parameters.
 *
 * @see org.hibernate.engine.query.spi.NativeQueryInterpreter#recognizeParameters
 */
public interface ParameterRecognizer {

	/**
	 * Called when an ordinal parameter is recognized
	 *
	 * @param sourcePosition The position within the query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void ordinalParameter(int sourcePosition);

	/**
	 * Called when a named parameter is recognized
	 *
	 * @param name The recognized parameter name
	 * @param sourcePosition The position within the query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void namedParameter(String name, int sourcePosition);

	/**
	 * Called when a JPA-style named parameter is recognized
	 *
	 * @param label The label (identifier) of the JPA-style parameter.  e.g. for a parameter `?2`, the label is `2`
	 * @param sourcePosition The position within the query
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void jpaPositionalParameter(int label, int sourcePosition);

	/**
	 * Called when a character that is not part of a parameter is recognized.
	 *
	 * @param character The recognized character
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void other(char character);

	/**
	 * Callback after all parsing is complete
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void complete() {
		// by default, nothing to do
	}
}
