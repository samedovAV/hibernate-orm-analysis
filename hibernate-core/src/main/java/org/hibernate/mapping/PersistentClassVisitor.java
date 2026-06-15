/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author max
 */
public interface PersistentClassVisitor {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(RootClass class1);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(UnionSubclass subclass);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(SingleTableSubclass subclass);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(JoinedSubclass subclass);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Subclass subclass);
}
