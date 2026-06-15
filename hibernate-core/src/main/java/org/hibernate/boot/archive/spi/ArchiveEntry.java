/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.archive.spi;


import java.net.URI;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Represent an entry in the archive.
///
/// @author Steve Ebersole
public interface ArchiveEntry {
	/// The entry name.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/// The relative name of the entry within the archive.
	/// Typically, what we are looking for here is the ClassLoader resource lookup name.
	///
	/// @return The name relative to the archive root
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getNameWithinArchive();

	/// URI reference to the entry.  Useful for externalizing reference to the entry.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	URI getUri();

	/// Get access to the stream for the entry
	///
	/// @return Obtain stream access to the entry
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	InputStreamAccess getStreamAccess();
}
