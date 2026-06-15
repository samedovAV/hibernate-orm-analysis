/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.TimeZoneColumn;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TimeZoneColumnAnnotation implements TimeZoneColumn {
	private String name;
	private boolean insertable;
	private boolean updatable;
	private String columnDefinition;
	private String table;
	private String options;
	private String comment;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TimeZoneColumnAnnotation(ModelsContext modelContext) {
		this.name = "";
		this.insertable = true;
		this.updatable = true;
		this.columnDefinition = "";
		this.table = "";
		this.options = "";
		this.comment = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TimeZoneColumnAnnotation(TimeZoneColumn annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.insertable = annotation.insertable();
		this.updatable = annotation.updatable();
		this.columnDefinition = annotation.columnDefinition();
		this.table = annotation.table();
		this.options = annotation.options();
		this.comment = annotation.comment();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TimeZoneColumnAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.insertable = (boolean) attributeValues.get( "insertable" );
		this.updatable = (boolean) attributeValues.get( "updatable" );
		this.columnDefinition = (String) attributeValues.get( "columnDefinition" );
		this.table = (String) attributeValues.get( "table" );
		this.options = (String) attributeValues.get( "options" );
		this.comment = (String) attributeValues.get( "comment" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return TimeZoneColumn.class;
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
	public boolean insertable() {
		return insertable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void insertable(boolean value) {
		this.insertable = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean updatable() {
		return updatable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void updatable(boolean value) {
		this.updatable = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String columnDefinition() {
		return columnDefinition;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void columnDefinition(String value) {
		this.columnDefinition = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String table() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void table(String value) {
		this.table = value;
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

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String comment() {
		return comment;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void comment(String value) {
		this.comment = value;
	}
}
