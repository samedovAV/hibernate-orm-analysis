/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface ResultSetMappingBindingDefinition {
	/**
	 * The ResultSet mapping name
	 *
	 * @return The name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	/**
	 * Get the JAXB mappings for each defined value return in the ResultSet mapping.
	 *
	 * The elements here will all be of type {@link NativeQueryReturn}.  However
	 * the generate JAXB bindings do not understand that as a commonality between all of the
	 * sub-element types.
	 *
	 * @return The value return JAXB mappings.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List getValueMappingSources();
}
