/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.engine.profile;

import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Identifies the association referenced by a {@link Fetch}.
 *
 * @author Steve Ebersole
 */
public class Association {
	private final EntityPersister owner;
	private final String associationPath;
	private final String role;

	/**
	 * Constructs an association defining what is to be fetched.
	 *
	 * @param owner The entity owning the association
	 * @param associationPath The path of the association, from the entity
	 */
	public Association(EntityPersister owner, String associationPath) {
		this.owner = owner;
		this.associationPath = associationPath;
		this.role = owner.getEntityName() + '.' + associationPath;
	}

	/**
	 * The persister of the owning entity.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityPersister getOwner() {
		return owner;
	}

	/**
	 * The property path
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAssociationPath() {
		return associationPath;
	}

	/**
	 * The fully qualified role name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getRole() {
		return role;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String toString() {
		return "Association[" + role + "]";
	}
}
