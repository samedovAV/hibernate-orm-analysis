/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.SQLUpdate;
import org.hibernate.boot.models.annotations.spi.CustomSqlDetails;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class SQLUpdateAnnotation implements SQLUpdate, CustomSqlDetails {
	private String sql;
	private boolean callable;
	private java.lang.Class<? extends org.hibernate.jdbc.Expectation> verify;
	private String table;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public SQLUpdateAnnotation(ModelsContext modelContext) {
		this.callable = false;
		this.verify = org.hibernate.jdbc.Expectation.class;
		this.table = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public SQLUpdateAnnotation(SQLUpdate annotation, ModelsContext modelContext) {
		this.sql = annotation.sql();
		this.callable = annotation.callable();
		this.verify = annotation.verify();
		this.table = annotation.table();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public SQLUpdateAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.sql = (String) attributeValues.get( "sql" );
		this.callable = (boolean) attributeValues.get( "callable" );
		//noinspection unchecked
		this.verify = (Class<? extends org.hibernate.jdbc.Expectation>) attributeValues.get( "verify" );
		this.table = (String) attributeValues.get( "table" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return SQLUpdate.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String sql() {
		return sql;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void sql(String value) {
		this.sql = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean callable() {
		return callable;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void callable(boolean value) {
		this.callable = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<? extends org.hibernate.jdbc.Expectation> verify() {
		return verify;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void verify(java.lang.Class<? extends org.hibernate.jdbc.Expectation> value) {
		this.verify = value;
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


}
