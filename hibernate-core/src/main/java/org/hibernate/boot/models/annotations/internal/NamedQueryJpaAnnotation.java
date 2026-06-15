/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.internal;

import java.lang.annotation.Annotation;
import java.util.Map;

import jakarta.persistence.LockModeType;
import jakarta.persistence.PessimisticLockScope;
import jakarta.persistence.QueryFlushMode;
import org.hibernate.boot.jaxb.mapping.spi.JaxbNamedHqlQueryImpl;
import org.hibernate.boot.models.JpaAnnotations;
import org.hibernate.boot.models.xml.internal.QueryProcessing;
import org.hibernate.boot.models.xml.internal.TypeHelper;
import org.hibernate.boot.models.xml.spi.XmlDocumentContext;
import org.hibernate.models.spi.ModelsContext;

import jakarta.persistence.NamedQuery;

import static org.hibernate.boot.models.internal.OrmAnnotationHelper.extractJdkValue;
import static org.hibernate.internal.util.NullnessHelper.coalesce;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

@SuppressWarnings({ "ClassExplicitlyAnnotation", "unused" })
@jakarta.annotation.Generated("org.hibernate.orm.build.annotations.ClassGeneratorProcessor")
public class NamedQueryJpaAnnotation implements NamedQuery {
	private String name;
	private String query;
	private java.lang.Class<?> resultClass;
	private String entityGraph;
	private LockModeType lockMode;
	private PessimisticLockScope lockScope;
	private QueryFlushMode flush;
	private jakarta.persistence.QueryHint[] hints;

	/**
	 * Used in creating dynamic annotation instances (e.g. from XML)
	 */
	public NamedQueryJpaAnnotation(ModelsContext modelContext) {
		this.resultClass = void.class;
		this.entityGraph = "";
		this.lockMode = LockModeType.NONE;
		this.lockScope = PessimisticLockScope.NORMAL;
		this.flush = QueryFlushMode.DEFAULT;
		this.hints = new jakarta.persistence.QueryHint[0];
	}

	/**
	 * Used in creating annotation instances from JDK variant
	 */
	public NamedQueryJpaAnnotation(NamedQuery annotation, ModelsContext modelContext) {
		this.name = annotation.name();
		this.query = annotation.query();
		this.resultClass = annotation.resultClass();
		this.entityGraph = annotation.entityGraph();
		this.lockMode = annotation.lockMode();
		this.lockScope = annotation.lockScope();
		this.flush = annotation.flush();
		this.hints = extractJdkValue( annotation, JpaAnnotations.NAMED_QUERY, "hints", modelContext );
	}

	/**
	 * Used in creating annotation instances from Jandex variant
	 */
	public NamedQueryJpaAnnotation(Map<String, Object> attributeValues, ModelsContext modelContext) {
		this.name = (String) attributeValues.get( "name" );
		this.query = (String) attributeValues.get( "query" );
		this.resultClass = (Class<?>) attributeValues.get( "resultClass" );
		this.entityGraph = (String) attributeValues.get( "entityGraph" );
		this.lockMode = (LockModeType) attributeValues.get( "lockMode" );
		this.lockScope = (PessimisticLockScope) attributeValues.get( "lockScope" );
		this.flush = (QueryFlushMode) attributeValues.get( "flush" );
		this.hints = (jakarta.persistence.QueryHint[]) attributeValues.get( "hints" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Class<? extends Annotation> annotationType() {
		return NamedQuery.class;
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
	public String query() {
		return query;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void query(String value) {
		this.query = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public java.lang.Class<?> resultClass() {
		return resultClass;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void resultClass(java.lang.Class<?> value) {
		this.resultClass = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public LockModeType lockMode() {
		return lockMode;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void lockMode(LockModeType value) {
		this.lockMode = value;
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PessimisticLockScope lockScope() {
		return lockScope;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void lockScope(PessimisticLockScope lockScope) {
		this.lockScope = lockScope;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public QueryFlushMode flush() {
		return flush;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void flush(QueryFlushMode value) {
		this.flush = value;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String entityGraph() {
		return entityGraph;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void entityGraph(String entityGraph) {
		this.entityGraph = entityGraph;
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
	public void apply(JaxbNamedHqlQueryImpl jaxbNamedQuery, XmlDocumentContext xmlDocumentContext) {
		name( jaxbNamedQuery.getName() );
		query( jaxbNamedQuery.getQuery() );

		resultClass( TypeHelper.resolveClassReference( jaxbNamedQuery.getResultClass(), xmlDocumentContext, void.class ) );
		entityGraph( jaxbNamedQuery.getEntityGraph() );

		lockMode( coalesce( jaxbNamedQuery.getLockMode(), LockModeType.NONE ) );
		lockScope( coalesce( jaxbNamedQuery.getLockScope(), PessimisticLockScope.NORMAL ) );

		hints( QueryProcessing.collectQueryHints( jaxbNamedQuery.getHints(), xmlDocumentContext ) );
	}
}
