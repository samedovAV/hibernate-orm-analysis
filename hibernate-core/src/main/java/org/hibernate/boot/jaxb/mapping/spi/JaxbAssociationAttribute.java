/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * JAXB binding interface for association attributes (to-one and plural mappings)
 *
 * @author Steve Ebersole
 */
public interface JaxbAssociationAttribute extends JaxbCascadableAttribute {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTargetEntity();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTargetEntity(String value);
}
