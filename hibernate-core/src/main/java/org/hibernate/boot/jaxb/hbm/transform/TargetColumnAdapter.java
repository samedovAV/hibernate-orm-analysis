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
interface TargetColumnAdapter {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setName(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTable(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setNullable(Boolean value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setUnique(Boolean value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setColumnDefinition(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setLength(Integer value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setPrecision(Integer value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setScale(Integer value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setDefault(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCheck(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setComment(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setRead(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setWrite(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setInsertable(Boolean value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setUpdatable(Boolean value);
}
