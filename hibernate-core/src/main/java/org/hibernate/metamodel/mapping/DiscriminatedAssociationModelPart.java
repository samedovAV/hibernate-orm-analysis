/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.sql.ast.tree.from.TableGroupJoinProducer;
import org.hibernate.sql.ast.tree.predicate.Predicate;
import org.hibernate.sql.results.graph.Fetchable;
import org.hibernate.sql.results.graph.FetchableContainer;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A discriminated association.  This is similar to an association to
 * a discriminator-subclass except that here the discriminator is kept on
 * the association side, not the target side
 *
 * Commonality between {@link org.hibernate.annotations.Any} and
 * {@link org.hibernate.annotations.ManyToAny} mappings.
 *
 * @author Steve Ebersole
 */
public interface DiscriminatedAssociationModelPart extends Discriminable, Fetchable, FetchableContainer, TableGroupJoinProducer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BasicValuedModelPart getKeyPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityMappingType resolveDiscriminatorValue(Object discriminatorValue);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object resolveDiscriminatorForEntityType(EntityMappingType entityMappingType);

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default boolean isSimpleJoinPredicate(Predicate predicate) {
		return predicate == null;
	}
}
