/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.loader.ast.internal;

import org.hibernate.LockOptions;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.engine.spi.SubselectFetch;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryOptionsAdapter;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
class SingleIdExecutionContext extends BaseExecutionContext {
	private final Object entityInstance;
	private final Object entityId;
	private final EntityMappingType rootEntityDescriptor;
	private final Boolean readOnly;
	private final LockOptions lockOptions;
	private final SubselectFetch.RegistrationHandler subSelectFetchableKeysHandler;

	public SingleIdExecutionContext(
			Object entityId,
			Object entityInstance,
			EntityMappingType rootEntityDescriptor,
			Boolean readOnly,
			LockOptions lockOptions,
			SubselectFetch.RegistrationHandler subSelectFetchableKeysHandler,
			SharedSessionContractImplementor session) {
		super( session );
		this.entityInstance = entityInstance;
		this.entityId = entityId;
		this.rootEntityDescriptor = rootEntityDescriptor;
		this.readOnly = readOnly;
		this.lockOptions = lockOptions;
		this.subSelectFetchableKeysHandler = subSelectFetchableKeysHandler;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntityInstance() {
		return entityInstance;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getEntityId() {
		return entityId;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public EntityMappingType getRootEntityDescriptor() {
		return rootEntityDescriptor;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return new QueryOptionsAdapter() {
			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Boolean isReadOnly() {
				return readOnly;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public LockOptions getLockOptions() {
				return lockOptions;
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerLoadingEntityHolder(EntityHolder holder) {
		subSelectFetchableKeysHandler.addKey( holder );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean upgradeLocks() {
		return true;
	}
}
