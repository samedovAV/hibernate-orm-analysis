/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.boot.MappingException;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIndexManyToAnyType;
import org.hibernate.boot.model.JavaTypeDescriptor;
import org.hibernate.boot.model.source.spi.AnyDiscriminatorSource;
import org.hibernate.boot.model.source.spi.AnyKeySource;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexNature;
import org.hibernate.boot.model.source.spi.PluralAttributeMapKeyManyToAnySource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.spi.MetadataBuildingContext;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeMapKeyManyToAnySourceImpl
		implements PluralAttributeMapKeyManyToAnySource {

	private static final HibernateTypeSource UNKNOWN = new HibernateTypeSource() {
		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getName() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Map<String, String> getParameters() {
			return null;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public JavaTypeDescriptor getJavaType() {
			return null;
		}
	};

	private final AnyDiscriminatorSource discriminatorSource;
	private final AnyKeySource keySource;


	public PluralAttributeMapKeyManyToAnySourceImpl(
			final MappingDocument mappingDocument,
			final PluralAttributeSourceMapImpl pluralAttributeSource,
			final JaxbHbmIndexManyToAnyType jaxbMapKeyManyToAnyMapping) {

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
						return jaxbMapKeyManyToAnyMapping.getColumn();
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
			private final HibernateTypeSource discriminatorTypeSource = new HibernateTypeSourceImpl( jaxbMapKeyManyToAnyMapping.getMetaType() );
			private final RelationalValueSource discriminatorRelationalValueSource = relationalValueSources.get( 0 );

			// the DTD/XSD currently do not allow discriminator mapping here
			private final Map<String,String> discriminatorValueMapping = Collections.emptyMap();
//		this.discriminatorValueMapping = new HashMap<String, String>();
//		for ( JaxbHbmAnyValueMappingType valueMapping : jaxbMapKeyManyToAnyMapping.getMetaValue() ) {
//			discriminatorValueMapping.put(
//					valueMapping.getValue(),
//					mappingDocument.qualifyClassName( valueMapping.getClazz() )
//			);
//		}
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
			private final HibernateTypeSource fkTypeSource = new HibernateTypeSourceImpl( jaxbMapKeyManyToAnyMapping.getIdType() );
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
	public Nature getMapKeyNature() {
		return Nature.ANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeIndexNature getNature() {
		return PluralAttributeIndexNature.MANY_TO_ANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSource getTypeInformation() {
		return UNKNOWN;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return null;
	}
}
