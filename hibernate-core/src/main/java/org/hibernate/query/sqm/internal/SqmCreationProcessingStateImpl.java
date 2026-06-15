/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.query.sqm.internal;

import org.hibernate.query.hql.internal.SqmPathRegistryImpl;
import org.hibernate.query.hql.spi.SqmCreationProcessingState;
import org.hibernate.query.hql.spi.SqmPathRegistry;
import org.hibernate.query.hql.spi.SqmCreationState;
import org.hibernate.query.sqm.tree.SqmQuery;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class SqmCreationProcessingStateImpl implements SqmCreationProcessingState {
	private final SqmCreationState creationState;
	private final SqmQuery<?> processingQuery;

	private final SqmPathRegistryImpl processingIndex;

	public SqmCreationProcessingStateImpl(
			SqmQuery<?> processingQuery,
			SqmCreationState creationState) {
		this.processingQuery = processingQuery;
		this.creationState = creationState;
		this.processingIndex = new SqmPathRegistryImpl( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCreationProcessingState getParentProcessingState() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmQuery<?> getProcessingQuery() {
		return processingQuery;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmCreationState getCreationState() {
		return creationState;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SqmPathRegistry getPathRegistry() {
		return processingIndex;
	}
}
