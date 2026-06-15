/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.boot.models.annotations.spi.AttributeMarker;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.ElementCollection;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class ElementCollectionJpaAnnotation implements ElementCollection, AttributeMarker.Fetchable {
	private java.lang.Class<?> targetClass;
	private jakarta.persistence.FetchType fetch;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public ElementCollectionJpaAnnotation(ModelsContext modelContext) {
		this.targetClass = void.class;
		this.fetch = jakarta.persistence.FetchType.LAZY;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public ElementCollectionJpaAnnotation(ElementCollection annotation, ModelsContext modelContext) {
		this.targetClass = annotation.targetClass();
		this.fetch = annotation.fetch();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public ElementCollectionJpaAnnotation(
			Map<String, Object> attributeValues,
			ModelsContext modelContext) {
		this.targetClass = (Class<?>) attributeValues.get( "targetClass" );
		this.fetch = (jakarta.persistence.FetchType) attributeValues.get( "fetch" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return ElementCollection.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> targetClass() {
		return targetClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void targetClass(java.lang.Class<?> value) {
		this.targetClass = value;
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


}
