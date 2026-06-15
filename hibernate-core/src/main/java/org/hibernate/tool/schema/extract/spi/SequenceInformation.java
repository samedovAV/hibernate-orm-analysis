/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.spi;

import org.hibernate.boot.model.relational.QualifiedSequenceName;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information about existing sequences.
 *
 * @author Steve Ebersole
 */
public interface SequenceInformation {

	/**
	 * The qualified sequence name.
	 *
	 * @return The sequence name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QualifiedSequenceName getSequenceName();

	/**
	 * Retrieve the extracted start value defined for the sequence.
	 *
	 * @return The extracted start value or null id the value could not be extracted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Number getStartValue();

	/**
	 * Retrieve the extracted minimum value defined for the sequence.
	 *
	 * @return The extracted minimum value or null id the value could not be extracted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Number getMinValue();

	/**
	 * Retrieve the extracted maximum value defined for the sequence.
	 *
	 * @return The extracted maximum value or null id the value could not be extracted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Number getMaxValue();

	/**
	 * Retrieve the extracted increment value defined for the sequence.
	 *
	 * @return The extracted increment value; use a negative number to indicate the increment could not be extracted.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Number getIncrementValue();
}
