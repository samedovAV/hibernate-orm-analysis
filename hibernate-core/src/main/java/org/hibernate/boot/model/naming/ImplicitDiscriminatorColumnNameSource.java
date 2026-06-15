/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Context for determining the implicit name of an entity's discriminator column.
 *
 * @author Steve Ebersole
 */
public interface ImplicitDiscriminatorColumnNameSource extends ImplicitNameSource {
	/**
	 * Access the naming for the entity
	 *
	 * @return The naming for the entity
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EntityNaming getEntityNaming();
}
