/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.query.internal.NamedHqlSelectionDefinitionImpl;
import org.hibernate.models.spi.AnnotationTarget;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.jpa.internal.util.FlushModeTypeHelper.interpretFlushMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Used to build NamedHqlSelectionDefinitionImpl references
/// from named queries defined in hbm.xml mappings.
///
/// @author Steve Ebersole
public class HqlQueryBuilder<E> extends AbstractNamedQueryBuilder<E, HqlQueryBuilder<E>> {
	private String hqlString;

	private String entityGraphName;

	private Integer firstResult;
	private Integer maxResults;

	private Map<String, String> parameterTypes;

	public HqlQueryBuilder(String name, AnnotationTarget location) {
		super( name, location );
	}

	public HqlQueryBuilder(String name) {
		super( name, null );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected HqlQueryBuilder<E> getThis() {
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getHqlString() {
		return hqlString;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HqlQueryBuilder<E> setHqlString(String hqlString) {
		this.hqlString = hqlString;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getEntityGraphName() {
		return entityGraphName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HqlQueryBuilder<E> setEntityGraphName(String entityGraphName) {
		this.entityGraphName = entityGraphName;
		return this;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HqlQueryBuilder<E> setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HqlQueryBuilder<E> setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public NamedHqlSelectionDefinitionImpl<E> build() {
		return new NamedHqlSelectionDefinitionImpl<>(
				getName(),
				getLocation() == null ? null : getLocation().getName(),
				hqlString,
				getResultClass(),
				entityGraphName,
				interpretFlushMode( flushMode ),
				timeout,
				comment,
				readOnly,
				fetchSize,
				firstResult,
				maxResults,
				cacheable,
				cacheMode,
				cacheRegion,
				null,
				null,
				null,
				null,
				parameterTypes,
				getHints()
		);
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addParameterTypeHint(String name, String type) {
		if ( parameterTypes == null ) {
			parameterTypes = new HashMap<>();
		}

		parameterTypes.put( name, type );
	}
}
