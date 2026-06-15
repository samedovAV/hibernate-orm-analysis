/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.Cacheable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class CacheableJpaAnnotation implements Cacheable {
	private boolean value;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public CacheableJpaAnnotation(ModelsContext modelContext) {
		this.value = true;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public CacheableJpaAnnotation(Cacheable annotation, ModelsContext modelContext) {
		this.value = annotation.value();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public CacheableJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.value = (boolean) attributeValues.get( "value" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Cacheable.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean value() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void value(boolean value) {
		this.value = value;
	}


}
