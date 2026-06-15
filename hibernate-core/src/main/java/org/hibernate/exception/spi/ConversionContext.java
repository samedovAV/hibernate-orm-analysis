/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.exception.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public interface ConversionContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ViolatedConstraintNameExtractor getViolatedConstraintNameExtractor();
}
