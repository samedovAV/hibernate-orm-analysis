/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.JoinFormula;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class JoinFormulaAnnotation implements JoinFormula {
	private String value;
	private String referencedColumnName;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public JoinFormulaAnnotation(ModelsContext modelContext) {
		this.referencedColumnName = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public JoinFormulaAnnotation(JoinFormula annotation, ModelsContext modelContext) {
		this.value = annotation.value();
		this.referencedColumnName = annotation.referencedColumnName();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public JoinFormulaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.value = (String) attributeValues.get( "value" );
		this.referencedColumnName = (String) attributeValues.get( "referencedColumnName" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return JoinFormula.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String value() {
		return value;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void value(String value) {
		this.value = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String referencedColumnName() {
		return referencedColumnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void referencedColumnName(String value) {
		this.referencedColumnName = value;
	}


}
