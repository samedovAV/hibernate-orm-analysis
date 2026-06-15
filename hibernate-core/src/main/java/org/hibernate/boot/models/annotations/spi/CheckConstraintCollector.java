/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import jakarta.persistence.CheckConstraint;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which define check-constraints
 *
 * @author Steve Ebersole
 */
public interface CheckConstraintCollector extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	CheckConstraint[] check();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void check(CheckConstraint[] value);
}
