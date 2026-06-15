/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.AnyDiscriminatorValue;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class AnyDiscriminatorValueAnnotation implements AnyDiscriminatorValue {
	private String discriminator;
	private java.lang.Class<?> entity;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public AnyDiscriminatorValueAnnotation(ModelsContext modelContext) {
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public AnyDiscriminatorValueAnnotation(AnyDiscriminatorValue annotation, ModelsContext modelContext) {
		this.discriminator = annotation.discriminator();
		this.entity = annotation.entity();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public AnyDiscriminatorValueAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.discriminator = (String) attributeValues.get( "discriminator" );
		this.entity = (Class<?>) attributeValues.get( "entity" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return AnyDiscriminatorValue.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String discriminator() {
		return discriminator;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void discriminator(String value) {
		this.discriminator = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> entity() {
		return entity;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void entity(java.lang.Class<?> value) {
		this.entity = value;
	}


}
