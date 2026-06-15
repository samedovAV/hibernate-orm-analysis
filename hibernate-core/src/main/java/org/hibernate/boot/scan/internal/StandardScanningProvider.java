/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.internal;

import org.hibernate.boot.scan.spi.Scanner;
import org.hibernate.boot.scan.spi.ScanningContext;
import org.hibernate.boot.scan.spi.ScanningProvider;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// Standard implementation of [ScanningProvider].
///
/// @author Steve Ebersole
public class StandardScanningProvider implements ScanningProvider {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Scanner builderScanner(ScanningContext scanningContext) {
		return new StandardScanner();
	}
}
