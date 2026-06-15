/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmSetType;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.Orderable;
import org.hibernate.boot.model.source.spi.PluralAttributeNature;
import org.hibernate.boot.model.source.spi.Sortable;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeSourceSetImpl extends AbstractPluralAttributeSourceImpl implements Orderable, Sortable {
	private final JaxbHbmSetType jaxbSet;

	public PluralAttributeSourceSetImpl(
			MappingDocument sourceMappingDocument,
			JaxbHbmSetType jaxbSet,
			AttributeSourceContainer container) {
		super( sourceMappingDocument, jaxbSet, container );
		this.jaxbSet = jaxbSet;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeNature getNature() {
		return PluralAttributeNature.SET;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isSorted() {
		String comparatorName = getComparatorName();
		return StringHelper.isNotEmpty( comparatorName )
				&& !comparatorName.equals( "unsorted" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getComparatorName() {
		return jaxbSet.getSort();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOrdered() {
		return StringHelper.isNotEmpty( getOrder() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOrder() {
		return jaxbSet.getOrderBy();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.SET;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return jaxbSet.getNode();
	}
}
