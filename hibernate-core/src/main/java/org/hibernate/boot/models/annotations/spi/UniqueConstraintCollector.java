/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import jakarta.persistence.UniqueConstraint;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which define unique-constraints
 *
 * @author Steve Ebersole
 */
public interface UniqueConstraintCollector extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	UniqueConstraint[] uniqueConstraints();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void uniqueConstraints(UniqueConstraint[] value);
}
