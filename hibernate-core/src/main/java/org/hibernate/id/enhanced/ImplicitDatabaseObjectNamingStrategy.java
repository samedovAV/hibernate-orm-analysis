/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.id.enhanced;

import java.util.Map;

import org.hibernate.Incubating;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.service.ServiceRegistry;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A naming strategy specifically for determining the implicit naming of
 * tables and sequences relating to enhanced identifier-generators.
 *
 * @author Steve Ebersole
 *
 * @since 6
 */
@Incubating
public interface ImplicitDatabaseObjectNamingStrategy {
	String DEF_SEQUENCE = "hibernate_sequence";

	/**
	 * Determine the implicit name for an identifier-generator sequence
	 *
	 * @see org.hibernate.id.enhanced.SequenceStyleGenerator
	 * @see org.hibernate.id.enhanced.SequenceStructure
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QualifiedName determineSequenceName(
			Identifier catalogName,
			Identifier schemaName,
			Map<?,?> configValues,
			ServiceRegistry serviceRegistry);

	/**
	 * Determine the implicit name for an identifier-generator table
	 *
	 * @see org.hibernate.id.enhanced.TableGenerator
	 * @see org.hibernate.id.enhanced.TableStructure
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	QualifiedName determineTableName(
			Identifier catalogName,
			Identifier schemaName,
			Map<?,?> configValues,
			ServiceRegistry serviceRegistry);
}
