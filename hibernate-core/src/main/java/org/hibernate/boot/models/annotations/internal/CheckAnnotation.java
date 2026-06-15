/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.Check;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class CheckAnnotation implements Check {
	private String name;
	private String constraints;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public CheckAnnotation(ModelsContext modelContext) {
		this.name = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public CheckAnnotation(Check annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.constraints = annotation.constraints();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public CheckAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.constraints = (String) attributeValues.get( "constraints" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Check.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String name() {
		return name;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void name(String value) {
		this.name = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String constraints() {
		return constraints;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void constraints(String value) {
		this.constraints = value;
	}


}
