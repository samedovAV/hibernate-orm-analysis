/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.tree.domain;

import org.hibernate.Incubating;
import org.hibernate.metamodel.model.domain.SingularPersistentAttribute;
import org.hibernate.query.sqm.SqmPathSource;
import org.hibernate.query.sqm.SqmJoinable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@Incubating
public interface SqmSingularPersistentAttribute<D, J>
		extends SingularPersistentAttribute<D, J>, SqmPersistentAttribute<D, J>,
				SqmJoinable<D,J>, SqmPathSource<J> {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SqmPathSource<J> getSqmPathSource();
}
