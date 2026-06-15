/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Common interface for all mappings that contain relational table information
 *
 * @author Steve Ebersole
 */
public interface TableInformationContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSchema();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCatalog();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSubselect();

}
