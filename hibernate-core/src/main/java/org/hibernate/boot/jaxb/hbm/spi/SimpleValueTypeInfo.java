/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface SimpleValueTypeInfo {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeAttribute();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbHbmTypeSpecificationType getType();
}
