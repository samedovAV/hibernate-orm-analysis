/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.model.source.spi.EntityNamingSource;
import org.hibernate.mapping.PersistentClass;

import static org.hibernate.internal.util.StringHelper.isNotEmpty;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of EntityNamingSource
 *
 * @author Steve Ebersole
 */
class EntityNamingSourceImpl implements EntityNamingSource {
	private final String entityName;
	private final String className;
	private final String jpaEntityName;

	private final String typeName;

	public EntityNamingSourceImpl(String entityName, String className, String jpaEntityName) {
		this.entityName = entityName;
		this.className = className;
		this.jpaEntityName = jpaEntityName;
		this.typeName = isNotEmpty( className ) ? className : entityName;
	}

	public EntityNamingSourceImpl(PersistentClass entityBinding) {
		this( entityBinding.getEntityName(),
				entityBinding.getClassName(),
				entityBinding.getJpaEntityName() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityName() {
		return entityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getClassName() {
		return className;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getJpaEntityName() {
		return jpaEntityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTypeName() {
		return typeName;
	}
}
