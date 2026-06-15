/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.spi;

import org.hibernate.query.QueryParameter;
import org.hibernate.query.named.NamedQueryMemento;
import org.hibernate.type.BindableType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface QueryParameterImplementor<T> extends QueryParameter<T> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void disallowMultiValuedBinding();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void applyAnticipatedType(BindableType<?> type);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NamedQueryMemento.ParameterMemento toMemento();
}
