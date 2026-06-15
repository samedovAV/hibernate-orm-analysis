/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmColumnType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIndexType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmListIndexType;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexNature;
import org.hibernate.boot.model.source.spi.PluralAttributeSequentialIndexSource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SizeSource;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 */
public class PluralAttributeSequentialIndexSourceImpl
		extends AbstractHbmSourceNode
		implements PluralAttributeSequentialIndexSource {
	private final int base;
	private final String xmlNodeName;
	private final HibernateTypeSourceImpl typeSource;
	private final List<RelationalValueSource> valueSources;

	public PluralAttributeSequentialIndexSourceImpl(
			MappingDocument sourceMappingDocument,
			final JaxbHbmListIndexType jaxbListIndex) {
		super( sourceMappingDocument );
		this.base = Integer.parseInt( jaxbListIndex.getBase() );
		this.xmlNodeName = null;
		this.typeSource = new HibernateTypeSourceImpl( "integer" );
		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					final List<JaxbHbmColumnType> columnElements = jaxbListIndex.getColumn() == null
							? Collections.emptyList()
							: Collections.singletonList( jaxbListIndex.getColumn() );

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.LIST_INDEX;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbListIndex.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return columnElements;
					}

				}
		);
	}

	public PluralAttributeSequentialIndexSourceImpl(
			MappingDocument sourceMappingDocument,
			final JaxbHbmIndexType jaxbIndex) {
		super( sourceMappingDocument );
		this.base = 0;
		this.xmlNodeName = null;
		if ( StringHelper.isEmpty( jaxbIndex.getType() ) ) {
			this.typeSource = new HibernateTypeSourceImpl( "integer" );
		}
		else {
			this.typeSource = new HibernateTypeSourceImpl( jaxbIndex.getType() );
		}
		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.INDEX;
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
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInInsertByDefault() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesIncludedInUpdateByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean areValuesNullableByDefault() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getBase() {
		return base;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeIndexNature getNature() {
		return PluralAttributeIndexNature.SEQUENTIAL;
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

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return valueSources;
	}
}
