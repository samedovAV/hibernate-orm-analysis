/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.stat.internal;

import org.hibernate.metamodel.model.domain.NavigableRole;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Utilities useful when dealing with stats.
 *
 * @author Steve Ebersole
 */
public class StatsHelper {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public static NavigableRole getRootEntityRole(EntityPersister entityDescriptor) {
		final String rootEntityName = entityDescriptor.getRootEntityName();
		return entityDescriptor.getEntityName().equals( rootEntityName )
				? entityDescriptor.getNavigableRole()
				: entityDescriptor.getFactory().getMappingMetamodel()
						.getEntityDescriptor( rootEntityName )
						.getNavigableRole();
	}

	private StatsHelper() {
	}
}
