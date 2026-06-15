/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.jdbc;

import java.io.InputStream;
import java.sql.Blob;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Wraps a binary stream to also provide the length which is needed when binding.
 *
 * @author Steve Ebersole
 */
public interface BinaryStream {
	/**
	 * Retrieve the input stream.
	 *
	 * @return The input stream
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InputStream getInputStream();

	/**
	 * Access to the bytes.
	 *
	 * @return The bytes.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	byte[] getBytes();

	/**
	 * Retrieve the length of the input stream
	 *
	 * @return The input stream length
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	long getLength();

	/**
	 * Release any underlying resources.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();

	/**
	 * Use the given {@link LobCreator} to create a {@link Blob}
	 * with the same data as this binary stream.
	 *
	 * @since 7.0
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Blob asBlob(LobCreator lobCreator);
}
