/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.spi;

import org.hibernate.models.spi.ClassDetails;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Registrations which are considered global, collected across annotations
 * and XML mappings.
 *
 * @author Steve Ebersole
 */
public interface GlobalRegistrations {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<LifecycleEventHandler> getEntityListenerRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<ClassDetails, List<LifecycleEventHandler>> getTargetedEntityListenerRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<ConversionRegistration> getConverterRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JavaTypeRegistration> getJavaTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<JdbcTypeRegistration> getJdbcTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<UserTypeRegistration> getUserTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<CompositeUserTypeRegistration> getCompositeUserTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<CollectionTypeRegistration> getCollectionTypeRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<EmbeddableInstantiatorRegistration> getEmbeddableInstantiatorRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, FilterDefRegistration> getFilterDefRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<FetchProfileRegistration> getFetchProfileRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, String> getImportedRenames();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, SequenceGeneratorRegistration> getSequenceGeneratorRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, TableGeneratorRegistration> getTableGeneratorRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, GenericGeneratorRegistration> getGenericGeneratorRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Set<ConverterRegistration> getJpaConverters();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, SqlResultSetMappingRegistration> getSqlResultSetMappingRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedQueryRegistration> getNamedQueryRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedNativeQueryRegistration> getNamedNativeQueryRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedStatementRegistration> getNamedStatementRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedNativeStatementRegistration> getNamedNativeStatementRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String, NamedStoredProcedureQueryRegistration> getNamedStoredProcedureQueryRegistrations();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	List<DatabaseObjectRegistration>  getDatabaseObjectRegistrations();

	// todo : named entity graphs

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> T as(Class<T> type);
}
