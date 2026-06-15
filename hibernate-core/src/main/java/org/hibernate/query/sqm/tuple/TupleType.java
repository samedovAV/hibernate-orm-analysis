/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tuple;

import jakarta.annotation.Nullable;
import org.hibernate.metamodel.model.domain.ReturnableType;
import org.hibernate.query.sqm.SqmBindableType;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes any structural type without a direct java type representation.
 *
 * @author Christian Beikov
 */
public interface TupleType<J> extends ReturnableType<J> {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int componentCount();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getComponentName(int index);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<String> getComponentNames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<?> get(int index);
	@Nullable @Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmBindableType<?> get(String componentName);
}
