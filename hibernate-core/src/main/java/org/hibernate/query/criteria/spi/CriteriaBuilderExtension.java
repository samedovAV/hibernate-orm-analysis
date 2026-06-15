/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.criteria.spi;

import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.service.Service;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Interface which allows extension of {@link HibernateCriteriaBuilder}
 * with additional functionality by registering a {@link Service}.
 *
 * @author Marco Belladelli
 */
public interface CriteriaBuilderExtension extends Service {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	HibernateCriteriaBuilder extend(HibernateCriteriaBuilder cb);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Class<? extends HibernateCriteriaBuilder> getRegistrationKey();
}
