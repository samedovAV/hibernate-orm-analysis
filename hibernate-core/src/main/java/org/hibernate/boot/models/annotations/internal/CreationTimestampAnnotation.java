/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class CreationTimestampAnnotation implements CreationTimestamp {
	private org.hibernate.annotations.SourceType source;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public CreationTimestampAnnotation(ModelsContext modelContext) {
		this.source = org.hibernate.annotations.SourceType.VM;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public CreationTimestampAnnotation(CreationTimestamp annotation, ModelsContext modelContext) {
		this.source = annotation.source();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public CreationTimestampAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.source = (org.hibernate.annotations.SourceType) attributeValues.get( "source" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return CreationTimestamp.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.SourceType source() {
		return source;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void source(org.hibernate.annotations.SourceType value) {
		this.source = value;
	}


}
