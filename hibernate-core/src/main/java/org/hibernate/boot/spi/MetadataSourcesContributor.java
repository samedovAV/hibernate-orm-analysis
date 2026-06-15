/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.spi;

import org.hibernate.boot.MetadataSources;
import org.hibernate.service.JavaServiceLoadable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A bootstrap process hook for contributing sources to {@link MetadataSources}.
 *
 * @author Steve Ebersole
 *
 * @since 5.0
 */
@JavaServiceLoadable
public interface MetadataSourcesContributor {
	/**
	 * Perform the process of contributing to the {@link MetadataSources}.
	 *
	 * @param metadataSources The {@code MetadataSource}s, to which to contribute.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void contribute(MetadataSources metadataSources);
}
