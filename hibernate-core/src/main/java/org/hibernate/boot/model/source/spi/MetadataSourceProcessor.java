/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Defines the steps in processing metadata sources.  The steps are performed
 * in a prerequisite series across all sources.  For example, the basic
 * requirement is custom types, so {@link #processTypeDefinitions()} is performed
 * first across all sources to build a complete set of types.  Then the next steps
 * can be performed.
 *
 * @author Steve Ebersole
 */
public interface MetadataSourceProcessor {
	/**
	 * A general preparation step.  Called first.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void prepare();

	/**
	 * Process all custom Type definitions.  This step has no
	 * prerequisites.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processTypeDefinitions();

	/**
	 * Process all explicit query renames (imports).  This step has no
	 * prerequisites.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processQueryRenames();

	/**
	 * Process all "root" named queries.  These are named queries not defined on
	 * a specific entity (which will be handled later during
	 * {@link #processEntityHierarchies}).
	 * <p>
	 * This step has no prerequisites.  The returns associated with named native
	 * queries can depend on entity binding being complete, but those are handled
	 * later during {@link #processResultSetMappings()}.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processNamedQueries();

	/**
	 * Process all {@link org.hibernate.boot.model.relational.AuxiliaryDatabaseObject} definitions.
	 * <p>
	 * This step has no prerequisites.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processAuxiliaryDatabaseObjectDefinitions();

	/**
	 * Process all custom identifier generator declarations,
	 * <p>
	 * Depends on {@link #processTypeDefinitions()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processIdentifierGenerators();

	/**
	 * Process all filter definitions.
	 * <p>
	 * This step depends on {@link #processTypeDefinitions()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processFilterDefinitions();

	/**
	 * Process all fetch profiles.
	 * <p>
	 * todo : does this step depend on any others??
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processFetchProfiles();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void prepareForEntityHierarchyProcessing();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processEntityHierarchies(Set<String> processedEntityNames);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void postProcessEntityHierarchies();

	/**
	 * Process ResultSet mappings for native queries.  At the moment, this
	 * step has {@link #processEntityHierarchies} as a prerequisite because
	 * the parsing of the returns access the entity bindings.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void processResultSetMappings();

	/**
	 * General finish up step.  Called last.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void finishUp();
}
