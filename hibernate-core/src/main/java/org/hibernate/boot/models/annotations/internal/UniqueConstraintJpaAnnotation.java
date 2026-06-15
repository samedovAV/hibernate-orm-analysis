/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.UniqueConstraint;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class UniqueConstraintJpaAnnotation implements UniqueConstraint {
	private String name;
	private String[] columnNames;
	private String options;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public UniqueConstraintJpaAnnotation(ModelsContext modelsContext) {
		this.name = "";
		this.options = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public UniqueConstraintJpaAnnotation(UniqueConstraint annotation, ModelsContext modelsContext) {
		this.name = annotation.name();
		this.columnNames = annotation.columnNames();
		this.options = annotation.options();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public UniqueConstraintJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelsContext) {
		this.name = (String) attributeValues.get( "name" );
		this.columnNames = (String[]) attributeValues.get( "columnNames" );
		this.options = (String) attributeValues.get( "options" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return UniqueConstraint.class;
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
	public String[] columnNames() {
		return columnNames;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void columnNames(String[] value) {
		this.columnNames = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String options() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void options(String value) {
		this.options = value;
	}


}
