/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.state.internal;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.RootClass;
import org.hibernate.metamodel.mapping.AuxiliaryMapping;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.metamodel.mapping.internal.MappingModelCreationProcess;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.temporal.TemporalTableStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * State management for temporal entities and collections with
 * {@linkplain TemporalTableStrategy#NATIVE dialect-native temporary tables}.
 *
 * @author Gavin King
 *
 * @since 7.4
 */
public final class NativeTemporalStateManagement extends AbstractStateManagement {
	public static final NativeTemporalStateManagement INSTANCE = new NativeTemporalStateManagement();

	private NativeTemporalStateManagement() {
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AuxiliaryMapping createAuxiliaryMapping(
			EntityPersister persister,
			RootClass rootClass,
			MappingModelCreationProcess creationProcess) {
		return TemporalStateManagement.INSTANCE.createAuxiliaryMapping( persister, rootClass, creationProcess);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AuxiliaryMapping createAuxiliaryMapping(
			PluralAttributeMapping pluralAttributeMapping,
			Collection bootDescriptor,
			MappingModelCreationProcess creationProcess) {
		return TemporalStateManagement.INSTANCE.createAuxiliaryMapping( pluralAttributeMapping, bootDescriptor, creationProcess);
	}
}
