/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.QueryCacheLayout;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class QueryCacheLayoutAnnotation implements QueryCacheLayout {
	private org.hibernate.annotations.CacheLayout layout;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public QueryCacheLayoutAnnotation(ModelsContext modelContext) {
		this.layout = org.hibernate.annotations.CacheLayout.AUTO;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public QueryCacheLayoutAnnotation(QueryCacheLayout annotation, ModelsContext modelContext) {
		this.layout = annotation.layout();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public QueryCacheLayoutAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.layout = (org.hibernate.annotations.CacheLayout) attributeValues.get( "layout" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return QueryCacheLayout.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.CacheLayout layout() {
		return layout;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void layout(org.hibernate.annotations.CacheLayout value) {
		this.layout = value;
	}


}
