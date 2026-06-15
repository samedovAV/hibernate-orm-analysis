/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A factory for instances of {@link Blob} and {@link Clob} used for writing LOB data.
 *
 * @author Steve Ebersole
 *
 * @see Hibernate#getLobHelper()
 */
public interface LobHelper {

	/**
	 * Create a new {@link Blob} from bytes.
	 *
	 * @param bytes a byte array
	 *
	 * @return the created Blob
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Blob createBlob(byte[] bytes);

	/**
	 * Create a new {@link Blob} from stream data.
	 *
	 * @param stream a binary stream
	 * @param length the number of bytes in the stream

	 * @return the create Blob
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Blob createBlob(InputStream stream, long length);

	/**
	 * Create a new {@link Clob} from content.
	 *
	 * @param string The string data
	 *
	 * @return The created {@link Clob}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Clob createClob(String string);

	/**
	 * Create a new {@link Clob} from character reader.
	 *
	 * @param reader a character stream
	 * @param length the number of characters in the stream
	 *
	 * @return The created {@link Clob}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Clob createClob(Reader reader, long length);

	/**
	 * Create a new {@link NClob} from content.
	 *
	 * @param string The string data
	 *
	 * @return The created {@link NClob}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NClob createNClob(String string);

	/**
	 * Create a new {@link NClob} from character reader.
	 *
	 * @param reader a character stream
	 * @param length the number of characters in the stream
	 *
	 * @return The created {@link NClob}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NClob createNClob(Reader reader, long length);
}
