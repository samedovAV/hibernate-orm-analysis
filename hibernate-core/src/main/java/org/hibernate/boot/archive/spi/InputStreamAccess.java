/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.archive.spi;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Contract for building InputStreams, especially in on-demand situations
///
/// @author Steve Ebersole
public interface InputStreamAccess {
	/// Get the name of the resource backing the stream
	///
	/// @return The backing resource name
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getStreamName();

	/// Get access to the stream.  Can be called multiple times, a different stream instance should be returned each time.
	///
	/// @return The stream
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InputStream accessInputStream();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default <X> X fromStream(Function<InputStream, X> action) {
		final InputStream inputStream = accessInputStream();
		try {
			return action.apply( inputStream );
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ignore) {
			}
		}
	}
}
