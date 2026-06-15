/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.hibernate.boot.jaxb.mapping.spi.JaxbJoinColumnImpl;
import org.hibernate.boot.jaxb.mapping.spi.JaxbJoinTableImpl;
import org.hibernate.boot.models.JpaAnnotations;
import org.hibernate.boot.models.annotations.spi.CommonTableDetails;
import org.hibernate.boot.models.xml.internal.db.ForeignKeyProcessing;
import org.hibernate.boot.models.xml.internal.db.JoinColumnProcessing;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.JoinTable;

import static org.hibernate.boot.models.internal.OrmAnnotationHelper.extractJdkValue;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.applyCatalog;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.applyOptionalString;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.applySchema;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.collectCheckConstraints;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.collectIndexes;
import static org.hibernate.boot.models.xml.internal.XmlAnnotationHelper.collectUniqueConstraints;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class JoinTableJpaAnnotation implements JoinTable, CommonTableDetails {
	private String name;
	private String catalog;
	private String schema;
	private jakarta.persistence.JoinColumn[] joinColumns;
	private jakarta.persistence.JoinColumn[] inverseJoinColumns;
	private jakarta.persistence.ForeignKey foreignKey;
	private jakarta.persistence.ForeignKey inverseForeignKey;
	private jakarta.persistence.UniqueConstraint[] uniqueConstraints;
	private jakarta.persistence.Index[] indexes;
	private jakarta.persistence.CheckConstraint[] check;
	private String comment;
	private String type;
	private String options;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public JoinTableJpaAnnotation(ModelsContext modelContext) {
		this.name = "";
		this.catalog = "";
		this.schema = "";
		this.joinColumns = new jakarta.persistence.JoinColumn[0];
		this.inverseJoinColumns = new jakarta.persistence.JoinColumn[0];
		this.foreignKey = JpaAnnotations.FOREIGN_KEY.createUsage( modelContext );
		this.inverseForeignKey = JpaAnnotations.FOREIGN_KEY.createUsage( modelContext );
		this.uniqueConstraints = new jakarta.persistence.UniqueConstraint[0];
		this.indexes = new jakarta.persistence.Index[0];
		this.check = new jakarta.persistence.CheckConstraint[0];
		this.comment = "";
		this.type = "";
		this.options = "";
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public JoinTableJpaAnnotation(JoinTable annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.catalog = annotation.catalog();
		this.schema = annotation.schema();
		this.joinColumns = extractJdkValue( annotation, JpaAnnotations.JOIN_TABLE, "joinColumns", modelContext );
		this.inverseJoinColumns = extractJdkValue(
				annotation,
				JpaAnnotations.JOIN_TABLE,
				"inverseJoinColumns",
				modelContext
		);
		this.foreignKey = extractJdkValue( annotation, JpaAnnotations.JOIN_TABLE, "foreignKey", modelContext );
		this.inverseForeignKey = extractJdkValue(
				annotation,
				JpaAnnotations.JOIN_TABLE,
				"inverseForeignKey",
				modelContext
		);
		this.uniqueConstraints = extractJdkValue(
				annotation,
				JpaAnnotations.JOIN_TABLE,
				"uniqueConstraints",
				modelContext
		);
		this.indexes = extractJdkValue( annotation, JpaAnnotations.JOIN_TABLE, "indexes", modelContext );
		this.check = extractJdkValue( annotation, JpaAnnotations.JOIN_TABLE, "check", modelContext );
		this.comment = annotation.comment();
		this.type = annotation.type();
		this.options = annotation.options();
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public JoinTableJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.catalog = (String) attributeValues.get( "catalog" );
		this.schema = (String) attributeValues.get( "schema" );
		this.joinColumns = (jakarta.persistence.JoinColumn[]) attributeValues.get( "joinColumns" );
		this.inverseJoinColumns = (jakarta.persistence.JoinColumn[]) attributeValues.get( "inverseJoinColumns" );
		this.foreignKey = (jakarta.persistence.ForeignKey) attributeValues.get( "foreignKey" );
		this.inverseForeignKey = (jakarta.persistence.ForeignKey) attributeValues.get( "inverseForeignKey" );
		this.uniqueConstraints = (jakarta.persistence.UniqueConstraint[]) attributeValues.get( "uniqueConstraints" );
		this.indexes = (jakarta.persistence.Index[]) attributeValues.get( "indexes" );
		this.check = (jakarta.persistence.CheckConstraint[]) attributeValues.get( "check" );
		this.comment = (String) attributeValues.get( "comment" );
		this.type = (String) attributeValues.get( "type" );
		this.options = (String) attributeValues.get( "options" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return JoinTable.class;
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
	public jakarta.persistence.JoinColumn[] joinColumns() {
		return joinColumns;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void joinColumns(jakarta.persistence.JoinColumn[] value) {
		this.joinColumns = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.JoinColumn[] inverseJoinColumns() {
		return inverseJoinColumns;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void inverseJoinColumns(jakarta.persistence.JoinColumn[] value) {
		this.inverseJoinColumns = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.ForeignKey foreignKey() {
		return foreignKey;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void foreignKey(jakarta.persistence.ForeignKey value) {
		this.foreignKey = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public jakarta.persistence.ForeignKey inverseForeignKey() {
		return inverseForeignKey;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void inverseForeignKey(jakarta.persistence.ForeignKey value) {
		this.inverseForeignKey = value;
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
	public jakarta.persistence.CheckConstraint[] check() {
		return check;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void check(jakarta.persistence.CheckConstraint[] value) {
		this.check = value;
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


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String type() {
		return type;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void type(String value) {
		this.type = value;
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
	public void apply(JaxbJoinTableImpl jaxbJoinTable, XmlDocumentContext xmlDocumentContext) {
		applyOptionalString( jaxbJoinTable.getName(), this::name );
		applyCatalog( jaxbJoinTable, this, xmlDocumentContext );
		applySchema( jaxbJoinTable, this, xmlDocumentContext );
		applyOptionalString( jaxbJoinTable.getComment(), this::comment );
		applyOptionalString( jaxbJoinTable.getOptions(), this::options );

		check( collectCheckConstraints( jaxbJoinTable.getCheckConstraints(), xmlDocumentContext ) );
		indexes( collectIndexes( jaxbJoinTable.getIndexes(), xmlDocumentContext ) );
		uniqueConstraints( collectUniqueConstraints( jaxbJoinTable.getUniqueConstraints(), xmlDocumentContext ) );

		final List<JaxbJoinColumnImpl> joinColumns = jaxbJoinTable.getJoinColumn();
		if ( CollectionHelper.isNotEmpty( joinColumns ) ) {
			joinColumns( JoinColumnProcessing.transformJoinColumnList(
					joinColumns,
					xmlDocumentContext
			) );
		}
		final List<JaxbJoinColumnImpl> inverseJoinColumns = jaxbJoinTable.getInverseJoinColumn();
		if ( CollectionHelper.isNotEmpty( inverseJoinColumns ) ) {
			inverseJoinColumns( JoinColumnProcessing.transformJoinColumnList(
					inverseJoinColumns,
					xmlDocumentContext
			) );
		}

		if ( jaxbJoinTable.getForeignKey() != null ) {
			foreignKey( ForeignKeyProcessing.createNestedForeignKeyAnnotation(
					jaxbJoinTable.getForeignKey(),
					xmlDocumentContext
			) );
		}
		if ( jaxbJoinTable.getInverseForeignKey() != null ) {
			inverseForeignKey( ForeignKeyProcessing.createNestedForeignKeyAnnotation(
					jaxbJoinTable.getInverseForeignKey(),
					xmlDocumentContext
			) );
		}
	}
}
