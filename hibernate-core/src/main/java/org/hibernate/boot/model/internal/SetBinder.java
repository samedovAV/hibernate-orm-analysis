/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.function.Supplier;

import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Set;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.usertype.UserCollectionType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link CollectionBinder} for {@link org.hibernate.collection.spi.PersistentSet sets},
 * whose mapping model type is {@link Set}.
 *
 * @author Matthew Inger
 */
public class SetBinder extends CollectionBinder {

	public SetBinder(
			Supplier<ManagedBean<? extends UserCollectionType>> customTypeBeanResolver,
			boolean sorted,
			MetadataBuildingContext buildingContext) {
		super( customTypeBeanResolver, sorted, buildingContext );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Collection createCollection(PersistentClass persistentClass) {
		return new Set( getCustomTypeBeanResolver(), persistentClass, getBuildingContext() );
	}

}
