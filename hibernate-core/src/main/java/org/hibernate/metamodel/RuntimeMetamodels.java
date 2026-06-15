/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.JpaMetamodel;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Entry point providing access to the runtime metamodels:
 * <ul>
 * <li>the {@linkplain JpaMetamodel domain model}, our implementation of the
 *     JPA-defined {@linkplain jakarta.persistence.metamodel.Metamodel model}
 *     of the Java types, and
 * <li>our {@linkplain MappingMetamodel relational mapping model} of how these
 *     types are made persistent.
 * </ul>
 *
 * @author Steve Ebersole
 */
@Incubating
public interface RuntimeMetamodels {
	/**
	 * Access to the JPA / domain metamodel.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JpaMetamodel getJpaMetamodel();

	/**
	 * Access to the relational mapping model.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingMetamodel getMappingMetamodel();

}
