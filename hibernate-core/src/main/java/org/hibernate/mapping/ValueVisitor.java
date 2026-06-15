/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;



/**
 * @author max
 *
 */
public interface ValueVisitor {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Bag bag);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(IdentifierBag bag);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(List list);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(PrimitiveArray primitiveArray);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Array list);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Map map);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(OneToMany many);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Set set);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Any any);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(SimpleValue value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Object accept(BasicValue value) {
		return null;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(DependantValue value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(Component component);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(ManyToOne mto);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object accept(OneToOne oto);


}
