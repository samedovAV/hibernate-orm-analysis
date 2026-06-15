/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.internal;

import org.hibernate.boot.scan.spi.ScanningResult;
import org.hibernate.internal.util.StringHelper;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// In-flight collection of [scan results][ScanningResult].
///
/// @author Steve Ebersole
public class ResultCollector {
	private final Set<String> discoveredPackages = new HashSet<>();
	private final Set<String> discoveredClasses = new HashSet<>();
	private final Set<URI> discoveredMappings = new HashSet<>();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addPackage(String packageName) {
		discoveredPackages.add( packageName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addClass(String className) {
		if ( className.endsWith( "package-info" ) ) {
			addPackage( StringHelper.qualifier( className ) );
		}
		else {
			discoveredClasses.add( className );
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addMapping(URI mappingUri) {
		discoveredMappings.add(mappingUri);
	}


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ScanningResult toResult() {
		return new ScanningResultImpl(
				Collections.unmodifiableSet( discoveredPackages ),
				Collections.unmodifiableSet( discoveredClasses ),
				Collections.unmodifiableSet( discoveredMappings )
		);
	}
}
