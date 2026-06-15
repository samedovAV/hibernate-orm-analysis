/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.util.List;

import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object that represents a subclass in a "union" or
 * {@linkplain jakarta.persistence.InheritanceType#TABLE_PER_CLASS "table per concrete class"}
 * inheritance hierarchy.
 *
 * @author Gavin King
 */
public final class UnionSubclass extends Subclass implements TableOwner {
	private Table table;

	public UnionSubclass(PersistentClass superclass, MetadataBuildingContext buildingContext) {
		super( superclass, buildingContext );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getTable() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setTable(Table table) {
		this.table = table;
		getSuperclass().addSubclassTable( table );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.util.Set<String> getSynchronizedTables() {
		return synchronizedTables;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected List<Property> getNonDuplicatedProperties() {
		return getPropertyClosure();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Table getIdentityTable() {
		return getTable();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object accept(PersistentClassVisitor mv) {
		return mv.accept(this);
	}
}
