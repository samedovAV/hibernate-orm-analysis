/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.Synchronize;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class SynchronizeAnnotation implements Synchronize {
	private String[] value;
	private boolean logical;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public SynchronizeAnnotation(ModelsContext modelContext) {
		this.logical = true;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public SynchronizeAnnotation(Synchronize annotation, ModelsContext modelContext) {
		this.value = annotation.value();
		this.logical = annotation.logical();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public SynchronizeAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.value = (String[]) attributeValues.get( "value" );
		this.logical = (boolean) attributeValues.get( "logical" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Synchronize.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] value() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void value(String[] value) {
		this.value = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean logical() {
		return logical;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void logical(boolean value) {
		this.logical = value;
	}


}
