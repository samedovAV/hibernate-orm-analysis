/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.scan.internal;

import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;
import org.hibernate.boot.scan.spi.ScanningContext;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Simple implementation of [ScanningContext].
///
/// @author Steve Ebersole
public final class ScanningContextImpl implements ScanningContext {
	private final ArchiveDescriptorFactory archiveDescriptorFactory;
	private final Map<Object, Object> properties;

	public ScanningContextImpl(
			ArchiveDescriptorFactory archiveDescriptorFactory,
			@SuppressWarnings("rawtypes") Map properties) {
		this.archiveDescriptorFactory = archiveDescriptorFactory;
		//noinspection unchecked
		this.properties = properties;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<Object, Object> getProperties() {
		return properties;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ArchiveDescriptorFactory getArchiveDescriptorFactory() {
		return archiveDescriptorFactory;
	}
}
