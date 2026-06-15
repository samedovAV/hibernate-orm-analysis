/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.Struct;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class StructAnnotation implements Struct {
	private String name;
	private String catalog;
	private String schema;
	private String[] attributes;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public StructAnnotation(ModelsContext modelContext) {
		this.attributes = new String[0];
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public StructAnnotation(Struct annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.catalog = annotation.catalog();
		this.schema = annotation.schema();
		this.attributes = annotation.attributes();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public StructAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.catalog = (String) attributeValues.get( "catalog" );
		this.schema = (String) attributeValues.get( "schema" );
		this.attributes = (String[]) attributeValues.get( "attributes" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return Struct.class;
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
	public String catalog() {
		return catalog;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void catalog(String value) {
		this.catalog = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String schema() {
		return schema;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void schema(String value) {
		this.schema = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String[] attributes() {
		return attributes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void attributes(String[] value) {
		this.attributes = value;
	}


}
