/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract describing source of table information
 *
 * @author Steve Ebersole
 */
public interface TableSource extends TableSpecificationSource {
	/**
	 * Obtain the supplied table name.
	 *
	 * @return The table name, or {@code null} is no name specified.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitTableName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getRowId();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCheckConstraint();
}
