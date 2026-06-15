/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import jakarta.persistence.NamedNativeStatement;
import org.hibernate.boot.jaxb.mapping.spi.JaxbNamedNativeStatementImpl;
import org.hibernate.boot.models.JpaAnnotations;
import org.hibernate.boot.models.xml.internal.QueryProcessing;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.models.spi.ModelsContext;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.hibernate.boot.models.internal.OrmAnnotationHelper.extractJdkValue;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class NamedNativeStatementJpaAnnotation implements NamedNativeStatement {
	private String name;
	private String statement;
	private jakarta.persistence.QueryHint[] hints;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public NamedNativeStatementJpaAnnotation(ModelsContext modelContext) {
		this.hints = new jakarta.persistence.QueryHint[0];
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public NamedNativeStatementJpaAnnotation(NamedNativeStatement annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.statement = annotation.statement();
		this.hints = extractJdkValue( annotation, JpaAnnotations.NAMED_NATIVE_STATEMENT, "hints", modelContext );
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public NamedNativeStatementJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.statement = (String) attributeValues.get( "statement" );
		this.hints = (jakarta.persistence.QueryHint[]) attributeValues.get( "hints" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return NamedNativeStatement.class;
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
	public String statement() {
		return statement;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void statement(String value) {
		this.statement = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.QueryHint[] hints() {
		return hints;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void hints(jakarta.persistence.QueryHint[] value) {
		this.hints = value;
	}


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply(JaxbNamedNativeStatementImpl jaxbStatement, XmlDocumentContext xmlDocumentContext) {
		name( jaxbStatement.getName() );
		statement( jaxbStatement.getStatement() );
		hints( QueryProcessing.collectQueryHints( jaxbStatement.getHints(), xmlDocumentContext ) );
	}
}
