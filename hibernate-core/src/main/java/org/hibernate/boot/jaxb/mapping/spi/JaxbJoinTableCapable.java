/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface JaxbJoinTableCapable {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbJoinTableImpl getJoinTable();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setJoinTable(JaxbJoinTableImpl value);
}
