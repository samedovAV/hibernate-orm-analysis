/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.sqm.tree.expression.SqmParameter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
// todo (6.0) : how is this different from org.hibernate.query.sqm.tree.jpa.ParameterCollector?
public interface ParameterCollector {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addParameter(SqmParameter<?> parameter);
}
