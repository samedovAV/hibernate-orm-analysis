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
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmColumnType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIdBagCollectionType;
import org.hibernate.boot.model.source.spi.AttributeSourceContainer;
import org.hibernate.boot.model.source.spi.CollectionIdSource;
import org.hibernate.boot.model.source.spi.ColumnSource;
import org.hibernate.boot.model.source.spi.Orderable;
import org.hibernate.boot.model.source.spi.PluralAttributeNature;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.boot.model.source.spi.SizeSource;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.CollectionHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeSourceIdBagImpl extends AbstractPluralAttributeSourceImpl implements Orderable {
	private final JaxbHbmIdBagCollectionType idBagMapping;
	private final CollectionIdSource collectionIdSource;

	public PluralAttributeSourceIdBagImpl(
			MappingDocument mappingDocument,
			final JaxbHbmIdBagCollectionType idBagMapping,
			AttributeSourceContainer container) {
		super( mappingDocument, idBagMapping, container );
		this.idBagMapping = idBagMapping;

		final RelationalValueSource collectionIdRelationalValueSource = RelationalValueSourceHelper.buildValueSource(
				sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.COLLECTION_ID;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return idBagMapping.getCollectionId().getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public SizeSource getSizeSource() {
						return Helper.interpretSizeSource(
								idBagMapping.getCollectionId().getLength(),
								(Integer) null,
								null
						);
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List<JaxbHbmColumnType> getColumnOrFormulaElements() {
						return idBagMapping.getCollectionId().getColumn();
					}
				}
		);

		if ( !(collectionIdRelationalValueSource instanceof ColumnSource) ) {
			throw new MappingException(
					String.format(
							Locale.ENGLISH,
							"Expecting column for collection id (idbag), but found formula [%s.%s]",
							container.getAttributeRoleBase().getFullPath(),
							idBagMapping.getName()
					),
					sourceMappingDocument().getOrigin()
			);
		}


		this.collectionIdSource = new CollectionIdSourceImpl(
				(ColumnSource) collectionIdRelationalValueSource,
				new HibernateTypeSourceImpl( idBagMapping.getCollectionId().getType() ),
				idBagMapping.getCollectionId().getGenerator().getClazz(),
				Helper.extractParameters( idBagMapping.getCollectionId().getGenerator().getConfigParameters() )
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeNature getNature() {
		return PluralAttributeNature.ID_BAG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public CollectionIdSource getCollectionIdSource() {
		return collectionIdSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isOrdered() {
		return StringHelper.isNotEmpty( getOrder() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOrder() {
		return idBagMapping.getOrderBy();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.ID_BAG;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return idBagMapping.getNode();
	}

	private static class CollectionIdSourceImpl implements CollectionIdSource {
		private final ColumnSource columnSource;
		private final HibernateTypeSourceImpl typeSource;
		private final String generator;
		private final Map<String, String> parameters;

		public CollectionIdSourceImpl(
				ColumnSource columnSource,
				HibernateTypeSourceImpl typeSource,
				String generator,
				final Map<String, String> parameters) {
			this.columnSource = columnSource;
			this.typeSource = typeSource;
			this.generator = generator;
			if ( CollectionHelper.isEmpty( parameters ) ) {
				this.parameters = Collections.emptyMap();
			}
			else {
				this.parameters = Collections.unmodifiableMap( parameters );
			}
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public ColumnSource getColumnSource() {
			return columnSource;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public HibernateTypeSourceImpl getTypeInformation() {
			return typeSource;
		}

		@Override
		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public String getGeneratorName() {
			return generator;
		}

		@Prove(complexity = Complexity.O_1, n = "", count = {})
		public Map<String, String> getParameters() {
			return parameters;
		}
	}
}
