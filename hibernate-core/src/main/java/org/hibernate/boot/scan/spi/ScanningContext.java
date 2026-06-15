/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.spi;

import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Access to information useful while performing discovery.  Acts as a
/// "parameter object" for [ScanningProvider#builderScanner(ScanningContext)]
///
/// @author Steve Ebersole
public interface ScanningContext {
	/// Access to all configuration properties defined on the context.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<Object,Object> getProperties();

	/// Access to the [ArchiveDescriptorFactory] defined for the context, providing
	/// the ability to interpret [URLs][java.net.URL] in an abstracted manner.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ArchiveDescriptorFactory getArchiveDescriptorFactory();
}
