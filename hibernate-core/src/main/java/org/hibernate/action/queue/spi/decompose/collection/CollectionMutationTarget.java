/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.decompose.collection;

import org.hibernate.Incubating;
import org.hibernate.action.queue.spi.meta.CollectionTableDescriptor;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.persister.collection.mutation.CollectionTableMapping;
import org.hibernate.sql.model.MutationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/// @author Steve Ebersole
/// @since 8.0
@Incubating
public interface CollectionMutationTarget extends MutationTarget<CollectionTableMapping, CollectionTableDescriptor> {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeMapping getTargetPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionTableMapping getCollectionTableMapping();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CollectionTableDescriptor getCollectionTableDescriptor();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CollectionTableMapping getIdentifierTableMapping() {
		return getCollectionTableMapping();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default CollectionTableDescriptor getIdentifierTableDescriptor() {
		return getCollectionTableDescriptor();
	}

	/// Whether the collection has at least one physical index column
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasPhysicalIndexColumn();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getIdentifierTableName();
}
