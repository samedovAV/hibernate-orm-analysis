/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.named;

import org.hibernate.query.sqm.tree.SqmStatement;

import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public interface NamedSqmQueryMemento<E> extends NamedQueryMemento<E> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getHqlString();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmStatement<E> getSqmStatement();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, String> getAnticipatedParameterTypes();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedSqmQueryMemento<E> makeCopy(String name);
}
