/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.mapping.spi.db;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.mapping.spi.JaxbCheckConstraintImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Composition of the aspects of column definition most commonly exposed in XSD "column types"
 *
 * @author Steve Ebersole
 */
public interface JaxbColumnCommon
		extends JaxbColumn, JaxbColumnMutable, JaxbCheckable, JaxbColumnNullable, JaxbColumnUniqueable, JaxbColumnDefinable, JaxbCommentable {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getTable() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isNullable() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isInsertable() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isUpdatable() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String getComment() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default Boolean isUnique() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default List<JaxbCheckConstraintImpl> getCheckConstraints() {
		return Collections.emptyList();
	}
}
