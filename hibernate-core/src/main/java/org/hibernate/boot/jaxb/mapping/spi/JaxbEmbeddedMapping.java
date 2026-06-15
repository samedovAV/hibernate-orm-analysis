/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * A model part that is (or can be) embeddable-valued (composite) - {@linkplain JaxbEmbeddedIdImpl},
 * {@linkplain JaxbEmbeddedIdImpl} and {@linkplain JaxbElementCollectionImpl}
 *
 * @author Steve Ebersole
 */
public interface JaxbEmbeddedMapping extends JaxbSingularAttribute {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTarget();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTarget(String target);
}
