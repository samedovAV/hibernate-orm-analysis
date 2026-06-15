/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.query.NamedNativeQueryDefinition;
import org.hibernate.boot.query.internal.NamedNativeSelectionDefinitionImpl;
import org.hibernate.models.spi.AnnotationTarget;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hibernate.jpa.internal.util.FlushModeTypeHelper.interpretFlushMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public
class NativeQueryBuilder<E> extends AbstractNamedQueryBuilder<E, NativeQueryBuilder<E>> {
	private String sqlString;

	private String resultSetMappingName;

	private Set<String> querySpaces;

	private Map<String, String> parameterTypes;
	private Integer firstResult;
	private Integer maxResults;

	public NativeQueryBuilder(String name, AnnotationTarget location) {
		super( name, location );
	}

	public NativeQueryBuilder(String name) {
		super( name, null );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> setSqlString(String sqlString) {
		this.sqlString = sqlString;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedNativeQueryDefinition<E> build() {
		return new NamedNativeSelectionDefinitionImpl<>(
				getName(),
				location == null ? null : location.getName(),
				sqlString,
				getResultClass(),
				resultSetMappingName,
				interpretFlushMode( flushMode ),
				getTimeout(),
				getComment(),
				getReadOnly(),
				getFetchSize(),
				firstResult,
				maxResults,
				getCacheable(),
				getCacheMode(),
				getCacheRegion(),
				null,
				null,
				null,
				null,
				getQuerySpaces(),
				getHints()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected NativeQueryBuilder<E> getThis() {
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSqlString() {
		return sqlString;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getQuerySpaces() {
		return querySpaces;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, String> getParameterTypes() {
		return parameterTypes == null ? Collections.emptyMap() : parameterTypes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getResultSetMappingName() {
		return resultSetMappingName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> addSynchronizedQuerySpace(String space) {
		if ( this.querySpaces == null ) {
			this.querySpaces = new HashSet<>();
		}
		this.querySpaces.add( space );
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> setQuerySpaces(Set<String> spaces) {
		this.querySpaces = spaces;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NativeQueryBuilder<E> setResultSetMappingName(String resultSetMappingName) {
		this.resultSetMappingName = resultSetMappingName;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addParameterTypeHint(String name, String type) {
		if ( parameterTypes == null ) {
			parameterTypes = new HashMap<>();
		}

		parameterTypes.put( name, type );
	}
}
