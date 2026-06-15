/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;

import java.io.Serializable;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
interface ColumnAndFormulaSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getColumnAttribute();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getFormulaAttribute();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<Serializable> getColumnOrFormula();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SourceColumnAdapter wrap(Serializable column);
}
