/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmBasicCollectionElementType;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.PluralAttributeElementNature;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceBasic;
import org.hibernate.boot.model.source.spi.PluralAttributeSource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.RelationalValueSourceContainer;
import org.hibernate.boot.model.source.spi.SizeSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeElementSourceBasicImpl
		extends AbstractHbmSourceNode
		implements PluralAttributeElementSourceBasic, RelationalValueSourceContainer {
	private final PluralAttributeSource pluralAttributeSource;
	private final HibernateTypeSourceImpl typeSource;
	private final List<RelationalValueSource> valueSources;

	public PluralAttributeElementSourceBasicImpl(
			MappingDocument sourceMappingDocument,
			PluralAttributeSource pluralAttributeSource,
			final JaxbHbmBasicCollectionElementType jaxbElement) {
		super( sourceMappingDocument );
		this.pluralAttributeSource = pluralAttributeSource;

		this.typeSource = new HibernateTypeSourceImpl( jaxbElement );

		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.ELEMENT;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbElement.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbElement.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbElement.getColumnOrFormula();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public Boolean isNullable() {
						return !jaxbElement.isNotNull();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public boolean isUnique() {
						return jaxbElement.isUnique();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public SizeSource getSizeSource() {
						return Helper.interpretSizeSource(
								jaxbElement.getLength(),
								jaxbElement.getScale(),
								jaxbElement.getPrecision()
						);
					}
				}
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.BASIC;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return valueSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInInsertByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInUpdateByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesNullableByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSourceImpl getExplicitHibernateTypeSource() {
		return typeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public AttributePath getAttributePath() {
		return pluralAttributeSource.getAttributePath();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCollectionElement() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public MetadataBuildingContext getBuildingContext() {
		return metadataBuildingContext();
	}
}
