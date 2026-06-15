/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * JAXB binding interface for natural-id definitions
 *
 * @author Steve Ebersole
 */
public interface JaxbNaturalId extends JaxbBaseAttributesContainer {
	/**
	 * The cache config associated with this natural-id
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbCachingImpl getCaching();

	/**
	 * Whether the natural-id (all attributes which are part of it) should
	 * be considered mutable or immutable.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMutable();
}
