/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import java.util.Map;

import org.hibernate.Incubating;
import org.hibernate.service.JavaServiceLoadable;
import org.hibernate.service.Service;
import org.hibernate.tool.schema.internal.exec.JdbcContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Contract for schema management tool integration.
 * <p>
 * A custom {@code SchemaManagementTool} may be selected either by setting the
 * configuration property
 * {@value org.hibernate.cfg.SchemaToolingSettings#SCHEMA_MANAGEMENT_TOOL}, or by
 * registering it as a {@linkplain java.util.ServiceLoader Java service}.
 *
 * @author Steve Ebersole
 */
@Incubating
@JavaServiceLoadable
public interface SchemaManagementTool extends Service {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SchemaCreator getSchemaCreator(Map<String,Object> options);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SchemaDropper getSchemaDropper(Map<String,Object> options);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SchemaMigrator getSchemaMigrator(Map<String,Object> options);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SchemaValidator getSchemaValidator(Map<String,Object> options);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SchemaPopulator getSchemaPopulator(Map<String,Object> options) {
		throw new UnsupportedOperationException("Schema populator is not supported by this schema management tool.");
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default SchemaTruncator getSchemaTruncator(Map<String,Object> options) {
		throw new UnsupportedOperationException("Schema truncator is not supported by this schema management tool.");
	}
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default GeneratorSynchronizer getSequenceSynchronizer(Map<String,Object> options) {
		throw new UnsupportedOperationException("Schema populator is not supported by this schema management tool.");
	}

	/**
	 * This allows to set an alternative implementation for the Database
	 * generation target.
	 * Used by Hibernate Reactive so that it can use the reactive database
	 * access rather than needing a JDBC connection.
	 * @param generationTarget the custom instance to use.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void setCustomDatabaseGenerationTarget(GenerationTarget generationTarget);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ExtractionTool getExtractionTool();

	/**
	 * Resolves the {@linkplain GenerationTarget targets} to which to
	 * send the DDL commands based on configuration
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default GenerationTarget[] buildGenerationTargets(
			TargetDescriptor targetDescriptor,
			JdbcContext jdbcContext,
			Map<String, Object> options,
			boolean needsAutoCommit) {
		throw new UnsupportedOperationException("Building generation targets is not supported by this schema management tool.");
	}
}
