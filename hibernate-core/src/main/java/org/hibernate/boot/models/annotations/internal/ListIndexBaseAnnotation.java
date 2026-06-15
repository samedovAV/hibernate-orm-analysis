/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.ListIndexBase;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class ListIndexBaseAnnotation implements ListIndexBase {
	private int value;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public ListIndexBaseAnnotation(ModelsContext modelContext) {
		this.value = 0;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public ListIndexBaseAnnotation(ListIndexBase annotation, ModelsContext modelContext) {
		this.value = annotation.value();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public ListIndexBaseAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.value = (int) attributeValues.get( "value" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return ListIndexBase.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int value() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void value(int value) {
		this.value = value;
	}


}
