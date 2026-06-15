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
interface ColumnAndFormulaTarget {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TargetColumnAdapter makeColumnAdapter(ColumnDefaults columnDefaults);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addColumn(TargetColumnAdapter column);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addFormula(String formula);
}
