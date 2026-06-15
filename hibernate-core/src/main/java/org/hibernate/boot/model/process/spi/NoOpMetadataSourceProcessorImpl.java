/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.process.spi;

import java.util.Set;

import org.hibernate.boot.model.source.spi.MetadataSourceProcessor;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A no-op implementation of MetadataSourceProcessor.
 * This is useful to replace other processors when they are disabled.
 */
final class NoOpMetadataSourceProcessorImpl implements MetadataSourceProcessor {

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processTypeDefinitions() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processQueryRenames() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processNamedQueries() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processAuxiliaryDatabaseObjectDefinitions() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processIdentifierGenerators() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processFilterDefinitions() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processFetchProfiles() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepareForEntityHierarchyProcessing() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processEntityHierarchies(Set<String> processedEntityNames) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void postProcessEntityHierarchies() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void processResultSetMappings() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void finishUp() {
	}

}
