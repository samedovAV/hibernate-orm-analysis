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
public interface JaxbSingularAssociationAttribute extends JaxbSingularAttribute, JaxbStandardAttribute, JaxbAssociationAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JaxbSingularFetchModeImpl getFetchMode();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setFetchMode(JaxbSingularFetchModeImpl mode);
}
