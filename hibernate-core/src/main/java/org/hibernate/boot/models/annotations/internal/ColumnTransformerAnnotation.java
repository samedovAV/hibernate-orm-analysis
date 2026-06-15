/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class ColumnTransformerAnnotation implements ColumnTransformer {
	private String forColumn;
	private String read;
	private String write;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public ColumnTransformerAnnotation(ModelsContext modelContext) {
		this.forColumn = "";
		this.read = "";
		this.write = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public ColumnTransformerAnnotation(ColumnTransformer annotation, ModelsContext modelContext) {
		this.forColumn = annotation.forColumn();
		this.read = annotation.read();
		this.write = annotation.write();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public ColumnTransformerAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.forColumn = (String) attributeValues.get( "forColumn" );
		this.read = (String) attributeValues.get( "read" );
		this.write = (String) attributeValues.get( "write" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return ColumnTransformer.class;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String forColumn() {
		return forColumn;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void forColumn(String value) {
		this.forColumn = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String read() {
		return read;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void read(String value) {
		this.read = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String write() {
		return write;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void write(String value) {
		this.write = value;
	}


}
