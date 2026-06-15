/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.boot.models.annotations.spi.AttributeMarker;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.Basic;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class BasicJpaAnnotation
		implements Basic, AttributeMarker, AttributeMarker.Fetchable, AttributeMarker.Optionalable {

	private jakarta.persistence.FetchType fetch;
	private boolean optional;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public BasicJpaAnnotation(ModelsContext modelContext) {
		this.fetch = jakarta.persistence.FetchType.EAGER;
		this.optional = true;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public BasicJpaAnnotation(Basic annotation, ModelsContext modelContext) {
		this.fetch = annotation.fetch();
		this.optional = annotation.optional();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public BasicJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.fetch = (jakarta.persistence.FetchType) attributeValues.get( "fetch" );
		this.optional = (boolean) attributeValues.get( "optional" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Basic.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.FetchType fetch() {
		return fetch;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void fetch(jakarta.persistence.FetchType value) {
		this.fetch = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean optional() {
		return optional;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void optional(boolean value) {
		this.optional = value;
	}


}
