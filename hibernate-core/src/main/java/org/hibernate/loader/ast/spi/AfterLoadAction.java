/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * An action to be performed after an entity has been loaded.  E.g. applying locks
 *
* @author Steve Ebersole
*/
public interface AfterLoadAction {
	/**
	 * The action trigger - the {@code entity} is being loaded
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void afterLoad(Object entity, EntityMappingType entityMappingType, SharedSessionContractImplementor session);
}
