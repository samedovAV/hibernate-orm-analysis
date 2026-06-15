/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.SecondaryRow;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class SecondaryRowAnnotation implements SecondaryRow {
	private String table;
	private boolean owned;
	private boolean optional;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public SecondaryRowAnnotation(ModelsContext modelContext) {
		this.table = "";
		this.owned = true;
		this.optional = true;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public SecondaryRowAnnotation(SecondaryRow annotation, ModelsContext modelContext) {
		this.table = annotation.table();
		this.owned = annotation.owned();
		this.optional = annotation.optional();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public SecondaryRowAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.table = (String) attributeValues.get( "table" );
		this.owned = (boolean) attributeValues.get( "owned" );
		this.optional = (boolean) attributeValues.get( "optional" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return SecondaryRow.class;
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
	public boolean owned() {
		return owned;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void owned(boolean value) {
		this.owned = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean optional() {
		return optional;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void optional(boolean value) {
		this.optional = value;
	}


}
