/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.xml.spi;

import org.hibernate.HibernateException;
import org.hibernate.boot.models.xml.internal.SimpleTypeInterpretation;
import org.hibernate.boot.models.xml.internal.XmlAnnotationHelper;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.boot.spi.EffectiveMappingDefaults;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.models.spi.MutableClassDetails;
import org.hibernate.models.spi.ModelsContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Context for a specific XML mapping file
 *
 * @author Steve Ebersole
 */
public interface XmlDocumentContext {
	/**
	 * The XML document
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	XmlDocument getXmlDocument();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	EffectiveMappingDefaults getEffectiveDefaults();

	/**
	 * Access to the containing ModelsContext
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ModelsContext getModelBuildingContext();

	/**
	 * Access to the containing BootstrapContext
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	BootstrapContext getBootstrapContext();

	/**
	 * Resolve a ClassDetails by name, accounting for XML-defined package name if one.
	 */
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	default MutableClassDetails resolveJavaType(String name) {
		try {
			return (MutableClassDetails) XmlAnnotationHelper.resolveJavaType( name, this );
		}
		catch (Exception e) {
			final HibernateException hibernateException = new HibernateException( "Unable to resolve Java type " + name );
			hibernateException.addSuppressed( e );
			throw hibernateException;
		}
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default String resolveClassName(String specifiedName) {
		final SimpleTypeInterpretation simpleTypeInterpretation = SimpleTypeInterpretation.interpret( specifiedName );
		if ( simpleTypeInterpretation != null ) {
			return simpleTypeInterpretation.getJavaType().getName();
		}

		if ( specifiedName.contains( "." ) ) {
			return specifiedName;
		}

		return StringHelper.qualifyConditionallyIfNot(
				getXmlDocument().getDefaults().getPackage(),
				specifiedName
		);
	}
}
