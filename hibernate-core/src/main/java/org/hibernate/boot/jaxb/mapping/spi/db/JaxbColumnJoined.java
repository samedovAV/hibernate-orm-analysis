/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;

import org.hibernate.boot.jaxb.mapping.spi.JaxbForeignKeyImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Composition of the aspects of column definition for join "column types" exposed in XSD
 *
 * @author Steve Ebersole
 */
public interface JaxbColumnJoined extends JaxbColumnCommon {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedColumnName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbForeignKeyImpl getForeignKey();
}
