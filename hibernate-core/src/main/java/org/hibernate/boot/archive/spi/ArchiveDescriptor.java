/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.archive.spi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.URL;
import java.util.function.Consumer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Models a logical archive, which might be
///   - a jar file
///   - a zip file
///   - an exploded directory
///   - etc
///
/// Used mainly for scanning purposes via [#visitClassEntries]
/// and locating "well known" resources via [#findEntry]
///
/// @author Steve Ebersole
/// @author Emmanuel Bernard
public interface ArchiveDescriptor {
	/// The URL which is the base of this archive.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	URL getUrl();

	/// Visit each entry in the archive which represents a class.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void visitClassEntries(Consumer<ArchiveEntry> entryConsumer);

	/// Resolve the given path relative to this archive.
	///
	/// @implNote Typically used to find `META-INF/persistence.xml` and `META-INF/orm.xml` files.
	@Nullable
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ArchiveEntry findEntry(String relativePath);

	/// Resolve a named archive relative to `this` archive.
	/// Generally the given name comes from the `META-INF/persistence.xml` within this archive
	/// using `<jar-file/>`.
	///
	/// @param jarFileReference The name given in `persistence.xml` via `<jar-file/>`
	@Nonnull @Prove(complexity = Complexity.O_1, n = "", count = {})
	ArchiveDescriptor resolveJarFileReference(@Nonnull String jarFileReference);
}
