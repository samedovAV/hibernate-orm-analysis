/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot;

import java.net.URL;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Abstraction for locating class-path resources
 *
 * @see ResourceStreamLocator
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface ResourceLocator {

	/**
	 * Locate the named resource
	 *
	 * @param resourceName The resource name to locate
	 *
	 * @return The located URL, or {@code null} if no match found
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	URL locateResource(String resourceName);
}
