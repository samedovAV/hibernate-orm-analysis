/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.Incubating;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Namespace;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object representing a named relational database array type.
 */
@Incubating
public class UserDefinedArrayType extends AbstractUserDefinedType {

	private Integer arraySqlTypeCode;
	private String elementTypeName;
	private Integer elementSqlTypeCode;
	private Integer elementDdlTypeCode;
	private Integer arrayLength;

	public UserDefinedArrayType(String contributor, Namespace namespace, Identifier physicalTypeName) {
		super( contributor, namespace, physicalTypeName );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getArraySqlTypeCode() {
		return arraySqlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setArraySqlTypeCode(Integer arraySqlTypeCode) {
		this.arraySqlTypeCode = arraySqlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getElementTypeName() {
		return elementTypeName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getElementSqlTypeCode() {
		return elementSqlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setElementSqlTypeCode(Integer elementSqlTypeCode) {
		this.elementSqlTypeCode = elementSqlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getElementDdlTypeCode() {
		return elementDdlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setElementDdlTypeCode(Integer elementDdlTypeCode) {
		this.elementDdlTypeCode = elementDdlTypeCode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getArrayLength() {
		return arrayLength;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setArrayLength(Integer arrayLength) {
		this.arrayLength = arrayLength;
	}
}
