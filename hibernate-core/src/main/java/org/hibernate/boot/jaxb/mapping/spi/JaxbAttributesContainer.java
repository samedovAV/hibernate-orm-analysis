/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * JAXB binding interface for commonality between things which contain attributes.
 *
 * @apiNote In the mapping XSD, this equates to the `attributes` and `embeddable-attributes`
 * nodes rather than the ManagedTypes themselves.
 *
 * @author Strong Liu
 * @author Steve Ebersole
 */
public interface JaxbAttributesContainer extends JaxbBaseAttributesContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbOneToOneImpl> getOneToOneAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbElementCollectionImpl> getElementCollectionAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbOneToManyImpl> getOneToManyAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbManyToManyImpl> getManyToManyAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbPluralAnyMappingImpl> getPluralAnyMappingAttributes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JaxbTransientImpl> getTransients();
}
