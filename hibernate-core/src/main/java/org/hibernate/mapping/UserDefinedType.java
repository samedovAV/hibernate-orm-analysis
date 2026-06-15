/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.mapping;

import java.io.Serializable;

import org.hibernate.Incubating;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.ContributableDatabaseObject;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A mapping model object which represents a user defined type.
 *
 * @see UserDefinedObjectType
 * @see UserDefinedArrayType
 */
@Incubating
public interface UserDefinedType extends Serializable, ContributableDatabaseObject {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQualifiedName(SqlStringGenerationContext context);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Identifier getNameIdentifier();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuotedName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuotedName(Dialect dialect);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QualifiedTableName getQualifiedTableName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isQuoted();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getSchema();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuotedSchema();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuotedSchema(Dialect dialect);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSchemaQuoted();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getCatalog();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getQuotedCatalog();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCatalogQuoted();
}
