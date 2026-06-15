/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Mapping of a discriminator value to the corresponding entity-name
 *
 * @author Steve Ebersole
 */
public interface JaxbDiscriminatorMapping {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getDiscriminatorValue();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCorrespondingEntityName();
}
