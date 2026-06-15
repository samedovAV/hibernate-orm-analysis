/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
interface ColumnDefaults {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isNullable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getLength();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getScale();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getPrecision();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isUnique();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isInsertable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Boolean isUpdatable();
}
