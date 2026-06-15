/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmBagCollectionType;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.Orderable;
import org.hibernate.boot.model.source.spi.PluralAttributeNature;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeSourceBagImpl extends AbstractPluralAttributeSourceImpl implements Orderable {
	private final JaxbHbmBagCollectionType jaxbBagMapping;

	public PluralAttributeSourceBagImpl(
			MappingDocument sourceMappingDocument,
			JaxbHbmBagCollectionType jaxbBagMapping,
			AttributeSourceContainer container) {
		super( sourceMappingDocument, jaxbBagMapping, container );
		this.jaxbBagMapping = jaxbBagMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeNature getNature() {
		return PluralAttributeNature.BAG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.BAG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return jaxbBagMapping.getNode();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOrdered() {
		return StringHelper.isNotEmpty( getOrder() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOrder() {
		return jaxbBagMapping.getOrderBy();
	}
}
