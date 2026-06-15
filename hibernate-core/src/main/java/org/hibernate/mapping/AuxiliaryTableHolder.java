/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Something that can have an associated auxiliary table,
 * for example, an audit table or a temporal history table.
 *
 * @author Gavin King
 * @author Marco Belladelli
 * @see Stateful
 * @since 7.4
 */
public interface AuxiliaryTableHolder {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table getAuxiliaryTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setAuxiliaryTable(Table auxiliaryTable);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Column getAuxiliaryColumn(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addAuxiliaryColumn(String name, Column column);
}
