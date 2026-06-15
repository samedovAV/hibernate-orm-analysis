/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.MappingException;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object that represents a subclass in a "joined" or
 * {@linkplain jakarta.persistence.InheritanceType#JOINED "table per subclass"}
 * inheritance hierarchy.
 *
 * @author Gavin King
 */
public final class JoinedSubclass extends Subclass implements TableOwner {
	private Table table;
	private KeyValue key;

	public JoinedSubclass(PersistentClass superclass, MetadataBuildingContext buildingContext) {
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
	public KeyValue getKey() {
		return key;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setKey(KeyValue key) {
		this.key = key;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void validate(Metadata mapping) throws MappingException {
		super.validate( mapping );
		if ( key != null && !key.isValid( mapping ) ) {
			throw new MappingException(
					"subclass key mapping has wrong number of columns: " +
					getEntityName() +
					" type: " +
					key.getType().getName()
				);
		}
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object accept(PersistentClassVisitor mv) {
		return mv.accept(this);
	}
}
