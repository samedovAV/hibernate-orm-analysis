/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import org.hibernate.Incubating;
import org.hibernate.dialect.Dialect;
import org.hibernate.generator.Generator;

import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model {@link Value} which may be treated as an identifying key of a
 * relational database table. A {@code KeyValue} might represent the primary key
 * of an entity or the foreign key of a collection, join table, secondary table,
 * or joined subclass table.
 *
 * @author Gavin King
 */
public interface KeyValue extends Value {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKey createForeignKeyOfEntity(String entityName, List<Column> referencedColumns);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKey createForeignKeyOfEntity(String entityName);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCascadeDeleteEnabled();

	enum NullValueSemantic { VALUE, NULL, NEGATIVE, UNDEFINED, NONE, ANY }

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	NullValueSemantic getNullValueSemantic();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getNullValue();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isUpdateable();

	@Incubating
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Generator createGenerator(Dialect dialect, RootClass rootClass, Property property, GeneratorSettings defaults);
}
