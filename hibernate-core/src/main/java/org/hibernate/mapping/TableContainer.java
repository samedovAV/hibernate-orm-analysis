/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Container for Table and Join reference
 *
 * @author Steve Ebersole
 */
public interface TableContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table findTable(String name);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Table getTable(String name);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Join findSecondaryTable(String name);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Join getSecondaryTable(String name);
}
