/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface JaxbColumnSizable extends JaxbColumn {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Integer getLength();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Integer getPrecision() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Integer getScale() {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Integer getSecondPrecision() {
		return null;
	}
}
