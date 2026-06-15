/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JaxbBaseAttributesContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbBasicImpl> getBasicAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbEmbeddedImpl> getEmbeddedAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbManyToOneImpl> getManyToOneAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbAnyMappingImpl> getAnyMappingAttributes();
}
