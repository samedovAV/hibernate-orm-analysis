/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.results.internal;

import org.hibernate.LockMode;
import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.engine.spi.EntityHolder;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.hibernate.resource.jdbc.spi.LogicalConnectionImplementor;
import org.hibernate.query.spi.QueryOptions;
import org.hibernate.query.spi.QueryParameterBindings;
import org.hibernate.sql.exec.internal.BaseExecutionContext;
import org.hibernate.sql.exec.spi.Callback;
import org.hibernate.sql.exec.spi.ExecutionContext;
import org.hibernate.sql.results.graph.InitializerData;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.hibernate.sql.results.jdbc.internal.JdbcValuesCacheHit;
import org.hibernate.sql.results.jdbc.spi.JdbcValues;
import org.hibernate.sql.results.jdbc.spi.JdbcValuesSourceProcessingState;
import org.hibernate.sql.results.jdbc.spi.RowProcessingState;
import org.hibernate.sql.results.spi.RowReader;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard RowProcessingState implementation
 */
public class RowProcessingStateStandardImpl extends BaseExecutionContext implements RowProcessingState {

	private final JdbcValuesSourceProcessingState resultSetProcessingState;

	private final RowReader<?> rowReader;
	private final JdbcValues jdbcValues;
	private final ExecutionContext executionContext;
	private final boolean needsResolveState;

	private final InitializerData[] initializerData;

	public RowProcessingStateStandardImpl(
			JdbcValuesSourceProcessingState resultSetProcessingState,
			ExecutionContext executionContext,
			RowReader<?> rowReader,
			JdbcValues jdbcValues) {
		super( resultSetProcessingState.getSession() );
		this.resultSetProcessingState = resultSetProcessingState;
		this.executionContext = executionContext;
		this.rowReader = rowReader;
		this.jdbcValues = jdbcValues;
		this.needsResolveState = !isQueryCacheHit()
				&& getQueryOptions().isResultCachingEnabled() == Boolean.TRUE;
		this.initializerData = new InitializerData[rowReader.getInitializerCount()];
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JdbcValuesSourceProcessingState getJdbcValuesSourceProcessingState() {
		return resultSetProcessingState;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockMode determineEffectiveLockMode(String alias) {
		if ( jdbcValues.usesFollowOnLocking() ) {
			// If follow-on locking is used, we must omit the lock options here,
			// because these lock options are only for Initializers.
			// If we wouldn't omit this, the follow-on lock requests would be no-ops,
			// because the EntityEntrys would already have the desired lock mode
			return LockMode.NONE;
		}
		final LockMode effectiveLockMode = resultSetProcessingState.getQueryOptions().getLockOptions().getLockMode();
		return effectiveLockMode == LockMode.NONE
				? jdbcValues.getValuesMapping().determineDefaultLockMode( alias, effectiveLockMode )
				: effectiveLockMode;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean needsResolveState() {
		return needsResolveState;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public <T extends InitializerData> T getInitializerData(int initializerId) {
		return (T) initializerData[initializerId];
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void setInitializerData(int initializerId, InitializerData state) {
		initializerData[initializerId] = state;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public RowReader<?> getRowReader() {
		return rowReader;
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean next() {
		return jdbcValues.next( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean previous() {
		return jdbcValues.previous( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean scroll(int i) {
		return jdbcValues.scroll( i, this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean position(int i) {
		return jdbcValues.position( i, this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public int getPosition() {
		return jdbcValues.getPosition();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isBeforeFirst() {
		return jdbcValues.isBeforeFirst( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void beforeFirst() {
		jdbcValues.beforeFirst( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isFirst() {
		return jdbcValues.isFirst( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean first() {
		return jdbcValues.first( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean last() {
		return jdbcValues.last( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isLast() {
		return jdbcValues.isLast( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterLast() {
		jdbcValues.afterLast( this );
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isAfterLast() {
		return jdbcValues.isAfterLast( this );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Object getJdbcValue(int position) {
		return jdbcValues.getCurrentRowValue( position );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void registerNonExists(EntityFetch fetch) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isQueryCacheHit() {
		return jdbcValues instanceof JdbcValuesCacheHit;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void finishRowProcessing(boolean wasAdded) {
		jdbcValues.finishRowProcessing( this, wasAdded );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryOptions getQueryOptions() {
		return executionContext.getQueryOptions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public QueryParameterBindings getQueryParameterBindings() {
		return getJdbcValuesSourceProcessingState().getExecutionContext().getQueryParameterBindings();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isScrollResult(){
		return executionContext.isScrollResult();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Callback getCallback() {
		return executionContext.getCallback();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasCallbackActions() {
		return executionContext.hasCallbackActions();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public CollectionKey getCollectionKey() {
		return executionContext.getCollectionKey();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getEntityInstance() {
		return executionContext.getEntityInstance();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getEntityId() {
		return executionContext.getEntityId();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getEntityUniqueKeyAttributePath() {
		return executionContext.getEntityUniqueKeyAttributePath();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public Object getEntityUniqueKey() {
		return executionContext.getEntityUniqueKey();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public EntityMappingType getRootEntityDescriptor() {
		return executionContext.getRootEntityDescriptor();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void registerLoadingEntityHolder(EntityHolder holder) {
		executionContext.registerLoadingEntityHolder( holder );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void afterStatement(LogicalConnectionImplementor logicalConnection) {
		executionContext.afterStatement( logicalConnection );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean hasQueryExecutionToBeAddedToStatistics() {
		return executionContext.hasQueryExecutionToBeAddedToStatistics();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean upgradeLocks() {
		return executionContext.upgradeLocks();
	}
}
