/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.MappingException;
import org.hibernate.property.access.internal.PropertyAccessStrategyIndexBackRefImpl;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Gavin King
 */
public class IndexBackref extends Property {
	private String collectionRole;
	private String entityName;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBackRef() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSynthetic() {
		return true;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCollectionRole() {
		return collectionRole;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setCollectionRole(String collectionRole) {
		this.collectionRole = collectionRole;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isBasicPropertyAccessor() {
		return false;
	}

	private PropertyAccessStrategy accessStrategy;

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PropertyAccessStrategy getPropertyAccessStrategy(Class clazz) throws MappingException {
		if ( accessStrategy == null ) {
			accessStrategy = new PropertyAccessStrategyIndexBackRefImpl( collectionRole, entityName );
		}
		return accessStrategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
}
