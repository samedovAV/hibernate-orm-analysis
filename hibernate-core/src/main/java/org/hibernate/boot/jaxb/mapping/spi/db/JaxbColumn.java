/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Base definition for XSD column mappings
 *
 * @author Steve Ebersole
 */
public interface JaxbColumn extends JaxbDatabaseObject {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getTable() {
		return null;
	}
}
