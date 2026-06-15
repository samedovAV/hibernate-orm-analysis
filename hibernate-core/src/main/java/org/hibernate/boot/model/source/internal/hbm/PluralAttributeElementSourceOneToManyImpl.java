/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmOneToManyCollectionElementType;
import org.hibernate.boot.model.source.spi.PluralAttributeElementNature;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceOneToMany;
import org.hibernate.boot.model.source.spi.PluralAttributeSource;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeElementSourceOneToManyImpl
		extends AbstractPluralAssociationElementSourceImpl
		implements PluralAttributeElementSourceOneToMany {
	private final JaxbHbmOneToManyCollectionElementType jaxbOneToManyElement;
	private final String referencedEntityName;
	private final String cascadeString;

	public PluralAttributeElementSourceOneToManyImpl(
			MappingDocument mappingDocument,
			final PluralAttributeSource pluralAttributeSource,
			final JaxbHbmOneToManyCollectionElementType jaxbOneToManyElement,
			String cascadeString) {
		super( mappingDocument, pluralAttributeSource );
		this.jaxbOneToManyElement = jaxbOneToManyElement;
		this.cascadeString = cascadeString;

		this.referencedEntityName = StringHelper.isNotEmpty( jaxbOneToManyElement.getEntityName() )
				? jaxbOneToManyElement.getEntityName()
				: mappingDocument.qualifyClassName( jaxbOneToManyElement.getClazz() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.ONE_TO_MANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIgnoreNotFound() {
		return jaxbOneToManyElement.getNotFound() != null && "ignore".equalsIgnoreCase( jaxbOneToManyElement.getNotFound().value() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return jaxbOneToManyElement.getNode();
	}
}
