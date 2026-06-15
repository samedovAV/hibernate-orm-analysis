/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.sql.internal;

import org.hibernate.metamodel.mapping.ModelPart;
import org.hibernate.spi.NavigablePath;
import org.hibernate.sql.ast.tree.from.TableGroup;
import org.hibernate.sql.results.graph.DomainResult;
import org.hibernate.sql.results.graph.DomainResultCreationState;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public abstract class AbstractSqmPathInterpretation<T> implements SqmPathInterpretation<T> {

	private final NavigablePath navigablePath;
	private final ModelPart mapping;
	private final TableGroup tableGroup;

	public AbstractSqmPathInterpretation(
			NavigablePath navigablePath,
			ModelPart mapping,
			TableGroup tableGroup) {
		assert navigablePath != null;
		assert mapping != null;
		assert tableGroup != null;

		this.navigablePath = navigablePath;
		this.mapping = mapping;
		this.tableGroup = tableGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public ModelPart getExpressionType() {
		return mapping;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public TableGroup getTableGroup() {
		return tableGroup;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public DomainResult<T> createDomainResult(
			String resultVariable,
			DomainResultCreationState creationState) {
		return mapping.createDomainResult(
				getNavigablePath(),
				tableGroup,
				resultVariable,
				creationState
		);
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void applySqlSelections(DomainResultCreationState creationState) {
		mapping.applySqlSelections( getNavigablePath(), tableGroup, creationState );
	}
}
