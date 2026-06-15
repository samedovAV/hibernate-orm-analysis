/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.boot.jaxb.mapping.spi.JaxbDiscriminatorColumnImpl;
import org.hibernate.boot.models.annotations.spi.ColumnDetails;
import org.hibernate.boot.models.annotations.spi.Commentable;
import org.hibernate.boot.models.annotations.spi.Optionable;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.DiscriminatorColumn;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class DiscriminatorColumnJpaAnnotation implements DiscriminatorColumn, ColumnDetails, Commentable, Optionable {

	private String name;
	private jakarta.persistence.DiscriminatorType discriminatorType;
	private String comment;
	private String columnDefinition;
	private String options;
	private int length;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public DiscriminatorColumnJpaAnnotation(ModelsContext modelContext) {
		this.name = "DTYPE";
		this.discriminatorType = jakarta.persistence.DiscriminatorType.STRING;
		this.comment = "";
		this.columnDefinition = "";
		this.options = "";
		this.length = 31;
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public DiscriminatorColumnJpaAnnotation(DiscriminatorColumn annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.discriminatorType = annotation.discriminatorType();
		this.comment = annotation.comment();
		this.columnDefinition = annotation.columnDefinition();
		this.options = annotation.options();
		this.length = annotation.length();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public DiscriminatorColumnJpaAnnotation(
			Map<String, Object> attributeValues,
			ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.discriminatorType = (jakarta.persistence.DiscriminatorType) attributeValues.get( "discriminatorType" );
		this.comment = (String) attributeValues.get( "comment" );
		this.columnDefinition = (String) attributeValues.get( "columnDefinition" );
		this.options = (String) attributeValues.get( "options" );
		this.length = (int) attributeValues.get( "length" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return DiscriminatorColumn.class;
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
	public jakarta.persistence.DiscriminatorType discriminatorType() {
		return discriminatorType;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void discriminatorType(jakarta.persistence.DiscriminatorType value) {
		this.discriminatorType = value;
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
	public String columnDefinition() {
		return columnDefinition;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void columnDefinition(String value) {
		this.columnDefinition = value;
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
	public int length() {
		return length;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void length(int value) {
		this.length = value;
	}


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply(JaxbDiscriminatorColumnImpl jaxbColumn, XmlDocumentContext xmlDocumentContext) {
		if ( StringHelper.isNotEmpty( jaxbColumn.getName() ) ) {
			name( jaxbColumn.getName() );
		}

		if ( jaxbColumn.getDiscriminatorType() != null ) {
			discriminatorType( jaxbColumn.getDiscriminatorType() );
		}

		if ( StringHelper.isNotEmpty( jaxbColumn.getComment() ) ) {
			comment( jaxbColumn.getComment() );
		}

		if ( jaxbColumn.getLength() != null ) {
			length( jaxbColumn.getLength() );
		}

		if ( StringHelper.isNotEmpty( jaxbColumn.getColumnDefinition() ) ) {
			columnDefinition( jaxbColumn.getColumnDefinition() );
		}
		if ( StringHelper.isNotEmpty( jaxbColumn.getOptions() ) ) {
			options( jaxbColumn.getOptions() );
		}
	}
}
