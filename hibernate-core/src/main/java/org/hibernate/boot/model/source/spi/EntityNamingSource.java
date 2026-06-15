/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.model.naming.EntityNaming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Naming information about an entity.
 *
 * @author Steve Ebersole
 */
public interface EntityNamingSource extends EntityNaming {
	/**
	 * Decode the name that we should expect to be used elsewhere to reference
	 * the modeled entity by decoding the entity-name/class-name combo.
	 *
	 * @return The reference-able type name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTypeName();
}
