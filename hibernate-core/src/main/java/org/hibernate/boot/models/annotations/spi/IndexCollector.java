/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;

import jakarta.persistence.Index;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which define indexes
 *
 * @author Steve Ebersole
 */
public interface IndexCollector extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Index[] indexes();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void indexes(Index[] value);
}
