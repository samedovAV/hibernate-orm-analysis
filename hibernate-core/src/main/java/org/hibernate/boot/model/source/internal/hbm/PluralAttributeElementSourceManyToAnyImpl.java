/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmAnyValueMappingType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmManyToAnyCollectionElementType;
import org.hibernate.boot.model.source.spi.AnyDiscriminatorSource;
import org.hibernate.boot.model.source.spi.AnyKeySource;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.PluralAttributeElementNature;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceManyToAny;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeElementSourceManyToAnyImpl
		implements PluralAttributeElementSourceManyToAny {
	private final String cascade;

	private final AnyDiscriminatorSource discriminatorSource;
	private final AnyKeySource keySource;

	public PluralAttributeElementSourceManyToAnyImpl(
			final MappingDocument mappingDocument,
			final AbstractPluralAttributeSourceImpl pluralAttributeSource,
			final JaxbHbmManyToAnyCollectionElementType jaxbManyToAnyMapping,
			String cascade) {
		this.cascade = cascade;

		final List<RelationalValueSource> relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MANY_TO_ANY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbManyToAnyMapping.getColumn();
					}
				}
		);

		// the list of relational values should contain 2 or more values:
		//		* the first represents the discriminator
		//		* the rest represent the fk

		if ( relationalValueSources.size() < 2 ) {
			throw new MappingException(
					String.format(
							Locale.ENGLISH,
							"<many-to-any /> mapping [%s] needs to specify 2 or more columns",
							pluralAttributeSource.getAttributeRole().getFullPath()
					),
					mappingDocument.getOrigin()
			);
		}

		this.discriminatorSource = new AnyDiscriminatorSource() {
			private final HibernateTypeSource discriminatorTypeSource = new HibernateTypeSourceImpl( jaxbManyToAnyMapping.getMetaType() );
			private final RelationalValueSource discriminatorRelationalValueSource = relationalValueSources.get( 0 );
			private final Map<String,String> discriminatorValueMapping = new HashMap<>();
			{
				for ( JaxbHbmAnyValueMappingType valueMapping : jaxbManyToAnyMapping.getMetaValue() ) {
					discriminatorValueMapping.put(
							valueMapping.getValue(),
							mappingDocument.qualifyClassName( valueMapping.getClazz() )
					);
				}
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public HibernateTypeSource getTypeSource() {
				return discriminatorTypeSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public RelationalValueSource getRelationalValueSource() {
				return discriminatorRelationalValueSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public Map<String, String> getValueMappings() {
				return discriminatorValueMapping;
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public AttributePath getAttributePath() {
				return pluralAttributeSource.getAttributePath();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public MetadataBuildingContext getBuildingContext() {
				return mappingDocument;
			}
		};

		this.keySource = new AnyKeySource() {
			private final HibernateTypeSource fkTypeSource = new HibernateTypeSourceImpl( jaxbManyToAnyMapping.getIdType() );
			private final List<RelationalValueSource> fkRelationalValueSources = relationalValueSources.subList( 1, relationalValueSources.size() );

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public HibernateTypeSource getTypeSource() {
				return fkTypeSource;
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public List<RelationalValueSource> getRelationalValueSources() {
				return fkRelationalValueSources;
			}

			@Override
			@Prove(complexity = Complexity.O_N, n = "", count = {})
			public AttributePath getAttributePath() {
				return pluralAttributeSource.getAttributePath();
			}

			@Override
			@Prove(complexity = Complexity.O_1, n = "", count = {})
			public MetadataBuildingContext getBuildingContext() {
				return mappingDocument;
			}
		};
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AnyDiscriminatorSource getDiscriminatorSource() {
		return discriminatorSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public AnyKeySource getKeySource() {
		return keySource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.MANY_TO_ANY;
	}
}
