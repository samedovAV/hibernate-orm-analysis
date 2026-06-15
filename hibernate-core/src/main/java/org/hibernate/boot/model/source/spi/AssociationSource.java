/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Gail Badner
 */
public interface AssociationSource {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributeSource getAttributeSource();

	/**
	 * Obtain the name of the referenced entity.
	 *
	 * @return The name of the referenced entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isIgnoreNotFound();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isMappedBy();
}
