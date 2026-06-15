/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.spi;

import org.hibernate.metamodel.mapping.EntityMappingType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Specialization of Loader for loading entities of a type
 *
 * @author Steve Ebersole
 */
public interface EntityLoader extends Loader {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType getLoadable();
}
