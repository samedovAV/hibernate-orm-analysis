/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.spi;

import org.hibernate.service.JavaServiceLoadable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// Provider for [Scanner] instances.
///
/// @author Steve Ebersole
@JavaServiceLoadable
public interface ScanningProvider {
	/// Create a scanner.
	///
	/// @param scanningContext The context of the scan, providing access to useful information.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Scanner builderScanner(ScanningContext scanningContext);
}
