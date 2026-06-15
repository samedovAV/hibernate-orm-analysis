/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.internal;

import java.util.List;
import java.util.function.Consumer;

import org.hibernate.metamodel.mapping.EmbeddableValuedModelPart;
import org.hibernate.query.named.FetchMemento;
import org.hibernate.query.results.spi.FetchBuilder;
import org.hibernate.query.results.internal.complete.CompleteFetchBuilderEmbeddableValuedModelPart;
import org.hibernate.spi.NavigablePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Christian Beikov
 */
public class FetchMementoEmbeddableStandard implements FetchMemento {
	private final NavigablePath navigablePath;
	private final EmbeddableValuedModelPart attributeMapping;
	private final List<String> columnNames;

	public FetchMementoEmbeddableStandard(
			NavigablePath navigablePath,
			EmbeddableValuedModelPart attributeMapping,
			List<String> columnNames) {
		this.navigablePath = navigablePath;
		this.attributeMapping = attributeMapping;
		this.columnNames = columnNames;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchBuilder resolve(
			Parent parent,
			Consumer<String> querySpaceConsumer,
			ResultSetMappingResolutionContext context) {
		return new CompleteFetchBuilderEmbeddableValuedModelPart( navigablePath, attributeMapping, columnNames );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EmbeddableValuedModelPart getAttributeMapping() {
		return attributeMapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> getColumnNames() {
		return columnNames;
	}
}
