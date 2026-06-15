/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import java.util.Set;

import org.hibernate.sql.ast.tree.from.TableGroupJoinProducer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality between `many-to-one`, `one-to-one` and `any`, as well as entity-valued collection elements and map-keys
 *
 * @author Steve Ebersole
 */
public interface EntityAssociationMapping extends ModelPart, Association, TableGroupJoinProducer {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getFetchableName() {
		return getPartName();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType getAssociatedEntityMappingType();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<String> getTargetKeyPropertyNames();

	/**
	 * The model sub-part relative to the associated entity type that is the target
	 * of this association's foreign-key
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelPart getKeyTargetMatchPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isReferenceToPrimaryKey();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isFkOptimizationAllowed();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean incrementFetchDepth(){
		return true;
	}
}
