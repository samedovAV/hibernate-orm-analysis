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

/**
 * @author Steve Ebersole
 */
public class ProvidedScannerProvider implements ScanningProvider {
	private final Scanner providedScanner;

	public ProvidedScannerProvider(Scanner providedScanner) {
		this.providedScanner = providedScanner;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Scanner builderScanner(ScanningContext scanningContext) {
		return providedScanner;
	}
}
