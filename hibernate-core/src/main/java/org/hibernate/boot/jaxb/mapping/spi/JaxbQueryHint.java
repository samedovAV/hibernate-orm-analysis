/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Models a named query hint in the JAXB model
 *
 * @author Steve Ebersole
 */
public interface JaxbQueryHint {
	/**
	 * The hint name.
	 *
	 * @see org.hibernate.jpa.AvailableHints
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * The hint value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getValue();
}
