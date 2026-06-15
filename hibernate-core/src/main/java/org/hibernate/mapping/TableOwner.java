/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Optional contract implemented by some subtypes of {@link PersistentClass}.
 * <p>
 * Differentiates entity types that map to their own table ({@link RootClass},
 * {@link UnionSubclass}, and {@link JoinedSubclass}) from those which do not
 * ({@link SingleTableSubclass}).
 *
 * @author Emmanuel Bernard
 * @author Steve Ebersole
 */
public interface TableOwner {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setTable(Table table);
}
