/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.hibernate.boot.jaxb.mapping.spi.JaxbTableGeneratorImpl;
import org.hibernate.boot.models.annotations.spi.IndexCollector;
import org.hibernate.boot.models.annotations.spi.UniqueConstraintCollector;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.TableGenerator;

import static org.hibernate.boot.models.JpaAnnotations.TABLE_GENERATOR;
import static org.hibernate.boot.models.internal.OrmAnnotationHelper.extractJdkValue;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.collectIndexes;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.collectUniqueConstraints;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class TableGeneratorJpaAnnotation implements TableGenerator, UniqueConstraintCollector, IndexCollector {
	private String name;
	private String table;
	private String catalog;
	private String schema;
	private String pkColumnName;
	private String valueColumnName;
	private String pkColumnValue;
	private int initialValue;
	private int allocationSize;
	private jakarta.persistence.UniqueConstraint[] uniqueConstraints;
	private jakarta.persistence.Index[] indexes;
	private String options;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public TableGeneratorJpaAnnotation(ModelsContext modelContext) {
		this( "", modelContext );
	}

	/**
	 * Used in creating named, defaulted annotation instances.  Generally this
	 * is a situation where we have:<ol>
	 *     <li>{@linkplain jakarta.persistence.GeneratedValue#strategy()} set to {@linkplain jakarta.persistence.GenerationType#TABLE}</li>
	 *     <li>{@linkplain jakarta.persistence.GeneratedValue#generator()} set to a non-empty String, but with no matching {@linkplain TableGenerator}</li>
	 * </ol>
	 */
	public TableGeneratorJpaAnnotation(String name, ModelsContext modelContext) {
		this.name = name;
		this.table = "";
		this.catalog = "";
		this.schema = "";
		this.pkColumnName = "";
		this.valueColumnName = "";
		this.pkColumnValue = "";
		this.initialValue = 0;
		this.allocationSize = 50;
		this.uniqueConstraints = new jakarta.persistence.UniqueConstraint[0];
		this.indexes = new jakarta.persistence.Index[0];
		this.options = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public TableGeneratorJpaAnnotation(TableGenerator annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.table = annotation.table();
		this.catalog = annotation.catalog();
		this.schema = annotation.schema();
		this.pkColumnName = annotation.pkColumnName();
		this.valueColumnName = annotation.valueColumnName();
		this.pkColumnValue = annotation.pkColumnValue();
		this.initialValue = annotation.initialValue();
		this.allocationSize = annotation.allocationSize();
		this.uniqueConstraints = extractJdkValue( annotation, TABLE_GENERATOR, "uniqueConstraints", modelContext );
		this.indexes = extractJdkValue( annotation, TABLE_GENERATOR, "indexes", modelContext );
		this.options = annotation.options();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public TableGeneratorJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.table = (String) attributeValues.get( "table" );
		this.catalog = (String) attributeValues.get( "catalog" );
		this.schema = (String) attributeValues.get( "schema" );
		this.pkColumnName = (String) attributeValues.get( "pkColumnName" );
		this.valueColumnName = (String) attributeValues.get( "valueColumnName" );
		this.pkColumnValue = (String) attributeValues.get( "pkColumnValue" );
		this.initialValue = (int) attributeValues.get( "initialValue" );
		this.allocationSize = (int) attributeValues.get( "allocationSize" );
		this.uniqueConstraints = (jakarta.persistence.UniqueConstraint[]) attributeValues.get( "uniqueConstraints" );
		this.indexes = (jakarta.persistence.Index[]) attributeValues.get( "indexes" );
		this.options = (String) attributeValues.get( "options" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return TableGenerator.class;
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
	public String table() {
		return table;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void table(String value) {
		this.table = value;
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
	public String pkColumnName() {
		return pkColumnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void pkColumnName(String value) {
		this.pkColumnName = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String valueColumnName() {
		return valueColumnName;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void valueColumnName(String value) {
		this.valueColumnName = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String pkColumnValue() {
		return pkColumnValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void pkColumnValue(String value) {
		this.pkColumnValue = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int initialValue() {
		return initialValue;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void initialValue(int value) {
		this.initialValue = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int allocationSize() {
		return allocationSize;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void allocationSize(int value) {
		this.allocationSize = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.UniqueConstraint[] uniqueConstraints() {
		return uniqueConstraints;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void uniqueConstraints(jakarta.persistence.UniqueConstraint[] value) {
		this.uniqueConstraints = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.Index[] indexes() {
		return indexes;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void indexes(jakarta.persistence.Index[] value) {
		this.indexes = value;
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


	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void apply(JaxbTableGeneratorImpl jaxbGenerator, XmlDocumentContext xmlDocumentContext) {
		if ( StringHelper.isNotEmpty( jaxbGenerator.getName() ) ) {
			name( jaxbGenerator.getName() );
		}
		if ( StringHelper.isNotEmpty( jaxbGenerator.getTable() ) ) {
			table( jaxbGenerator.getTable() );
		}
		if ( StringHelper.isNotEmpty( jaxbGenerator.getCatalog() ) ) {
			catalog( jaxbGenerator.getCatalog() );
		}
		if ( StringHelper.isNotEmpty( jaxbGenerator.getSchema() ) ) {
			schema( jaxbGenerator.getSchema() );
		}

		if ( StringHelper.isNotEmpty( jaxbGenerator.getPkColumnName() ) ) {
			pkColumnName( jaxbGenerator.getPkColumnName() );
		}
		if ( StringHelper.isNotEmpty( jaxbGenerator.getPkColumnValue() ) ) {
			pkColumnValue( jaxbGenerator.getPkColumnValue() );
		}

		if ( StringHelper.isNotEmpty( jaxbGenerator.getValueColumnName() ) ) {
			valueColumnName( jaxbGenerator.getValueColumnName() );
		}

		if ( jaxbGenerator.getInitialValue() != null ) {
			initialValue( jaxbGenerator.getInitialValue() );
		}
		if ( jaxbGenerator.getAllocationSize() != null ) {
			allocationSize( jaxbGenerator.getAllocationSize() );
		}

		if ( StringHelper.isNotEmpty( jaxbGenerator.getOptions() ) ) {
			options( jaxbGenerator.getOptions() );
		}

		uniqueConstraints( collectUniqueConstraints( jaxbGenerator.getUniqueConstraints(), xmlDocumentContext ) );
		indexes( collectIndexes( jaxbGenerator.getIndexes(), xmlDocumentContext ) );
	}
}
