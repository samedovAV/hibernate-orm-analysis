/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.internal;

import org.hibernate.boot.archive.spi.ArchiveDescriptor;
import org.hibernate.boot.jaxb.configuration.spi.JaxbPersistenceImpl;
import org.hibernate.boot.scan.spi.Scanner;
import org.hibernate.boot.scan.spi.ScanningResult;

import java.net.URL;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Standard implementation of [Scanner] used in cases
///
/// @author Steve Ebersole
public class StandardScanner implements Scanner {
	public StandardScanner() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ScanningResult scan(URL... boundaries) {
		return ScanningResult.NONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ScanningResult jpaScan(ArchiveDescriptor rootArchive, JaxbPersistenceImpl.JaxbPersistenceUnitImpl jaxbUnit) {
		return ScanningResult.NONE;
	}
}
