/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface EntityNaming {
	/**
	 * Retrieve the fully-qualified entity class name.  Note that for
	 * dynamic entities, this may return (what???).
	 *
	 * todo : what should this return for dynamic entities?  null?  The entity name?
	 *
	 * @return The entity class name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getClassName();

	/**
	 * The Hibernate entity name.  This might be either:<ul>
	 *     <li>The explicitly specified entity name, if one</li>
	 *     <li>The unqualified entity class name if no entity name was explicitly specified</li>
	 * </ul>
	 *
	 * @return The Hibernate entity name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getEntityName();

	/**
	 * The JPA-specific entity name.  See {@link jakarta.persistence.Entity#name()} for details.
	 *
	 * @return The JPA entity name, if one was specified.  May return {@code null} if one
	 * was not explicitly specified.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getJpaEntityName();
}
