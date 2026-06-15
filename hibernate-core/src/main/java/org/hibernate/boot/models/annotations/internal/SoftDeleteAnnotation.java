/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class SoftDeleteAnnotation implements SoftDelete {
	private String columnName;
	private String options;
	private String comment;
	private org.hibernate.annotations.SoftDeleteType strategy;
	private java.lang.Class<? extends jakarta.persistence.AttributeConverter<java.lang.Boolean, ?>> converter;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public SoftDeleteAnnotation(ModelsContext modelContext) {
		this.columnName = "";
		this.strategy = org.hibernate.annotations.SoftDeleteType.DELETED;
		this.converter = org.hibernate.annotations.SoftDelete.UnspecifiedConversion.class;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public SoftDeleteAnnotation(SoftDelete annotation, ModelsContext modelContext) {
		this.columnName = annotation.columnName();
		this.strategy = annotation.strategy();
		this.options = annotation.options();
		this.comment = annotation.comment();
		this.converter = annotation.converter();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public SoftDeleteAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.columnName = (String) attributeValues.get( "columnName" );
		this.strategy = (org.hibernate.annotations.SoftDeleteType) attributeValues.get( "strategy" );
		this.options = (String) attributeValues.get( "options" );
		this.comment = (String) attributeValues.get( "comment" );
		this.converter = (Class<? extends jakarta.persistence.AttributeConverter<Boolean, ?>>) attributeValues.
				get( "converter" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return SoftDelete.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String columnName() {
		return columnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void columnName(String value) {
		this.columnName = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String options() {
		return options;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void options(String options) {
		this.options = options;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String comment() {
		return comment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void comment(String comment) {
		this.comment = comment;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public org.hibernate.annotations.SoftDeleteType strategy() {
		return strategy;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void strategy(org.hibernate.annotations.SoftDeleteType value) {
		this.strategy = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<? extends jakarta.persistence.AttributeConverter<java.lang.Boolean, ?>> converter() {
		return converter;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void converter(java.lang.Class<? extends jakarta.persistence.AttributeConverter<java.lang.Boolean, ?>> value) {
		this.converter = value;
	}


}
