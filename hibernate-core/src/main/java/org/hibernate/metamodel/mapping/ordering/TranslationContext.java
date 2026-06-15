/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping.ordering;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.spi.JpaCompliance;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Access to information needed while translating a collection's order-by fragment
 *
 * @author Steve Ebersole
 */
public interface TranslationContext {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getFactory();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default JpaCompliance getJpaCompliance() {
		return getFactory().getSessionFactoryOptions().getJpaCompliance();
	}
}
