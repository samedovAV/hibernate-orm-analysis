/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.transform;


import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class EntityTypeInfo extends ManagedTypeInfo {
	private final PersistentClass persistentClass;

	public EntityTypeInfo(
			Table table,
			PersistentClass persistentClass) {
		super( table );
		this.persistentClass = persistentClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PersistentClass getPersistentClass() {
		return persistentClass;
	}
}
