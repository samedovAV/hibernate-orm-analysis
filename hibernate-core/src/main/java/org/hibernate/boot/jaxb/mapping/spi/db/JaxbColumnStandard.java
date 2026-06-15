/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Composition of the aspects of column definition for standard "column types" exposed in XSD
 *
 * @author Steve Ebersole
 */
public interface JaxbColumnStandard
		extends JaxbColumn, JaxbColumnMutable, JaxbCheckable, JaxbColumnNullable, JaxbColumnUniqueable,
		JaxbColumnDefinable, JaxbColumnSizable, JaxbColumnDefaultable, JaxbCommentable {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRead();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getWrite();
}
