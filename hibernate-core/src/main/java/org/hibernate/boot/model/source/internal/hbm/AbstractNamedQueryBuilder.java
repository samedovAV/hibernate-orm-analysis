/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Timeout;
import org.hibernate.CacheMode;

import jakarta.annotation.Nullable;
import org.hibernate.models.spi.AnnotationTarget;
import jakarta.persistence.QueryFlushMode;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractNamedQueryBuilder<R, T extends AbstractNamedQueryBuilder<R, T>> {
	protected final String name;
	protected final AnnotationTarget location;

	protected @Nullable Class<R> resultClass;

	protected Boolean cacheable;
	protected String cacheRegion;
	protected CacheMode cacheMode;

	protected QueryFlushMode flushMode;
	protected Boolean readOnly;

	protected Timeout timeout;
	protected Integer fetchSize;

	protected String comment;

	protected Map<String, Object> hints;

	public AbstractNamedQueryBuilder(String name, AnnotationTarget location) {
		this.name = name;
		this.location = location;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getName() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AnnotationTarget getLocation() {
		return location;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract T getThis();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setResultClass(Class<R> resultClass) {
		if ( resultClass != void.class ) {
			this.resultClass = resultClass;
		}
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setCacheable(Boolean cacheable) {
		this.cacheable = cacheable;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setCacheRegion(String cacheRegion) {
		this.cacheRegion = cacheRegion;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setCacheMode(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setTimeout(Timeout timeout) {
		this.timeout = timeout;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setFlushMode(QueryFlushMode flushMode) {
		this.flushMode = flushMode;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T setComment(String comment) {
		this.comment = comment;
		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<R> getResultClass() {
		return resultClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getCacheable() {
		return cacheable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCacheRegion() {
		return cacheRegion;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CacheMode getCacheMode() {
		return cacheMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryFlushMode getFlushMode() {
		return flushMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean getReadOnly() {
		return readOnly;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Timeout getTimeout() {
		return timeout;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Integer getFetchSize() {
		return fetchSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComment() {
		return comment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void addHint(String name, Object value) {
		if ( hints == null ) {
			hints = new HashMap<>();
		}
		hints.put( name, value );
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public T addHints(Map<String, Object> hintsMap) {
		if ( hints == null ) {
			hints = new HashMap<>();
		}
		hints.putAll( hintsMap );

		return getThis();
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Map<String, Object> getHints() {
		return hints;
	}
}
