/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc;

import java.io.Reader;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Wraps a character stream (reader) to also provide the length (number of characters) which is needed
 * when binding.
 *
 * @author Steve Ebersole
 */
public interface CharacterStream {
	/**
	 * Provides access to the underlying data as a Reader.
	 *
	 * @return The reader.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Reader asReader();

	/**
	 * Provides access to the underlying data as a String.
	 *
	 * @return The underlying String data
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String asString();

	/**
	 * Retrieve the number of characters.
	 *
	 * @return The number of characters.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getLength();

	/**
	 * Release any underlying resources.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();
}
