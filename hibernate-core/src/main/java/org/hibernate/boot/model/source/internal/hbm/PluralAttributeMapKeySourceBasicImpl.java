/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIndexType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmMapKeyBasicType;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexNature;
import org.hibernate.boot.model.source.spi.PluralAttributeMapKeySourceBasic;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SizeSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 */
public class PluralAttributeMapKeySourceBasicImpl
		extends AbstractHbmSourceNode
		implements PluralAttributeMapKeySourceBasic {
	private final HibernateTypeSourceImpl typeSource;
	private final List<RelationalValueSource> valueSources;

	private final String xmlNodeName;

	public PluralAttributeMapKeySourceBasicImpl(
			MappingDocument sourceMappingDocument,
			final JaxbHbmMapKeyBasicType jaxbMapKey) {
		super( sourceMappingDocument );
		this.typeSource = new HibernateTypeSourceImpl( jaxbMapKey );
		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MAP_KEY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbMapKey.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbMapKey.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbMapKey.getColumnOrFormula();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public SizeSource getSizeSource() {
						return Helper.interpretSizeSource(
								jaxbMapKey.getLength(),
								(Integer) null,
								null
						);
					}
				}
		);
		this.xmlNodeName = jaxbMapKey.getNode();
	}

	public PluralAttributeMapKeySourceBasicImpl(MappingDocument sourceMappingDocument, final JaxbHbmIndexType jaxbIndex) {
		super( sourceMappingDocument );
		this.typeSource = new HibernateTypeSourceImpl( jaxbIndex.getType() );
		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MAP_KEY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbIndex.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public SizeSource getSizeSource() {
						return Helper.interpretSizeSource(
								jaxbIndex.getLength(),
								(Integer) null,
								null
						);
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbIndex.getColumn();
					}
				}
		);
		this.xmlNodeName = null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeIndexNature getNature() {
		return PluralAttributeIndexNature.BASIC;
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
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSourceImpl getTypeInformation() {
		return typeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return xmlNodeName;
	}
}
