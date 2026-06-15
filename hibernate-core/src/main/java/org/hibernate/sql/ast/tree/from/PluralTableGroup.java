/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.ast.tree.from;

import org.hibernate.metamodel.mapping.CollectionPart;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public interface PluralTableGroup extends TableGroup {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	PluralAttributeMapping getModelPart();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableGroup getElementTableGroup();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableGroup getIndexTableGroup();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default TableGroup getTableGroup(CollectionPart.Nature nature) {
		switch ( nature ) {
			case ELEMENT:
				return getElementTableGroup();
			case INDEX:
				return getIndexTableGroup();
		}

		throw new IllegalStateException( "Could not find table group for: " + nature );
	}
}
