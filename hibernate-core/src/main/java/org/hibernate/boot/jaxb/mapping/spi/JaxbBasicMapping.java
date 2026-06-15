/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A model part that is (or can be) basic-valued - {@linkplain JaxbIdImpl}, {@linkplain JaxbBasicImpl} and
 * {@linkplain JaxbElementCollectionImpl}
 *
 * @author Steve Ebersole
 */
public interface JaxbBasicMapping {
	/**
	 * The attribute's name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setName(String name);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbUserTypeImpl getType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setType(JaxbUserTypeImpl value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTarget();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTarget(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJavaType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJavaType(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJdbcType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJdbcType(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getJdbcTypeCode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJdbcTypeCode(Integer value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJdbcTypeName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJdbcTypeName(String value);
}
