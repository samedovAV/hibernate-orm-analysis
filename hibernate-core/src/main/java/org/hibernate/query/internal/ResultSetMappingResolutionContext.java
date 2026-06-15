/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.MappingMetamodel;
import org.hibernate.query.named.NamedObjectRepository;
import org.hibernate.type.spi.TypeConfiguration;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Context ("parameter object") used in resolving a {@link NamedResultSetMappingMementoImpl}
 *
 * @author Steve Ebersole
 */
@FunctionalInterface
public interface ResultSetMappingResolutionContext {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SessionFactoryImplementor getSessionFactory();

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default MappingMetamodel getMappingMetamodel() {
		return getSessionFactory().getMappingMetamodel();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default TypeConfiguration getTypeConfiguration() {
		return getSessionFactory().getTypeConfiguration();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default NamedObjectRepository getNamedObjectRepository() {
		return getSessionFactory().getQueryEngine().getNamedObjectRepository();
	}
}
