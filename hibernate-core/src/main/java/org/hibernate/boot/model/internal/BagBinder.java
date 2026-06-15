/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.internal;

import java.util.function.Supplier;

import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.Bag;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.usertype.UserCollectionType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link CollectionBinder} for {@link org.hibernate.collection.spi.PersistentBag bags},
 * whose mapping model type is {@link Bag}.
 *
 * @author Matthew Inger
 */
public class BagBinder extends CollectionBinder {

	public BagBinder(
			Supplier<ManagedBean<? extends UserCollectionType>> customTypeBeanResolver,
			MetadataBuildingContext context) {
		super( customTypeBeanResolver, false, context );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Collection createCollection(PersistentClass owner) {
		return new Bag( getCustomTypeBeanResolver(), owner, getBuildingContext() );
	}
}
