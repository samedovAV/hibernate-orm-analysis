/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.model.domain.spi;

import jakarta.persistence.EntityGraph;
import org.hibernate.graph.spi.RootGraphImplementor;
import org.hibernate.metamodel.MappingMetamodel;
import org.hibernate.metamodel.model.domain.JpaMetamodel;

import java.util.List;
import java.util.Map;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * SPI extending {@link JpaMetamodel}.
 *
 * @author Steve Ebersole
 */
public interface JpaMetamodelImplementor extends JpaMetamodel {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	MappingMetamodel getMappingMetamodel();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	RootGraphImplementor<?> findEntityGraphByName(String name);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void addNamedEntityGraph(String graphName, RootGraphImplementor<?> rootGraph);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> List<EntityGraph<? super T>> findEntityGraphsByJavaType(Class<T> entityClass);
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	<T> Map<String, EntityGraph<? extends T>> getNamedEntityGraphs(Class<T> entityClass);
}
