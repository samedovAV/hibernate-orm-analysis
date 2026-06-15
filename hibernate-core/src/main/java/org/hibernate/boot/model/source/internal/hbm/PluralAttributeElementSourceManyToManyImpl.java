/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmFilterType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmManyToManyCollectionElementType;
import org.hibernate.boot.model.source.spi.FetchCharacteristics;
import org.hibernate.boot.model.source.spi.FilterSource;
import org.hibernate.boot.model.source.spi.PluralAttributeElementNature;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceManyToMany;
import org.hibernate.boot.model.source.spi.PluralAttributeSource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 * @author Gail Badner
 */
public class PluralAttributeElementSourceManyToManyImpl
		extends AbstractPluralAssociationElementSourceImpl
		implements PluralAttributeElementSourceManyToMany {
	private static final FilterSource[] NO_FILTER_SOURCES = new FilterSource[0];

	private final JaxbHbmManyToManyCollectionElementType jaxbManyToManyElement;
	private final String referencedEntityName;

	private final FetchCharacteristics fetchCharacteristics;

	private final List<RelationalValueSource> valueSources;
	private final FilterSource[] filterSources;

	public PluralAttributeElementSourceManyToManyImpl(
			MappingDocument mappingDocument,
			final PluralAttributeSource pluralAttributeSource,
			final JaxbHbmManyToManyCollectionElementType jaxbManyToManyElement) {
		super( mappingDocument, pluralAttributeSource );
		this.jaxbManyToManyElement = jaxbManyToManyElement;
		this.referencedEntityName = StringHelper.isNotEmpty( jaxbManyToManyElement.getEntityName() )
				? jaxbManyToManyElement.getEntityName()
				: mappingDocument.qualifyClassName( jaxbManyToManyElement.getClazz() );

		this.fetchCharacteristics = FetchCharacteristicsSingularAssociationImpl.interpretManyToManyElement(
				mappingDocument.getEffectiveDefaults(),
				jaxbManyToManyElement.getFetch(),
				jaxbManyToManyElement.getOuterJoin(),
				jaxbManyToManyElement.getLazy()
		);

		this.valueSources = RelationalValueSourceHelper.buildValueSources(
				sourceMappingDocument(),
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MANY_TO_MANY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public boolean isUnique() {
						return jaxbManyToManyElement.isUnique();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbManyToManyElement.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbManyToManyElement.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbManyToManyElement.getColumnOrFormula();
					}
				}
		);
		this.filterSources = buildFilterSources();
	}

	@Prove(complexity = Complexity.O_N, n = "", count = {})
	private FilterSource[] buildFilterSources() {
		final int size = jaxbManyToManyElement.getFilter().size();
		if ( size == 0 ) {
			return NO_FILTER_SOURCES;
		}

		FilterSource[] results = new FilterSource[size];
		for ( int i = 0; i < size; i++ ) {
			JaxbHbmFilterType element = jaxbManyToManyElement.getFilter().get( i );
			results[i] = new FilterSourceImpl( sourceMappingDocument(), element );
		}
		return results;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeElementNature getNature() {
		return PluralAttributeElementNature.MANY_TO_MANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FilterSource[] getFilterSources() {
		return filterSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityAttributeName() {
		return jaxbManyToManyElement.getPropertyRef();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return valueSources;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isIgnoreNotFound() {
		return jaxbManyToManyElement.getNotFound() != null && "ignore".equalsIgnoreCase( jaxbManyToManyElement.getNotFound().value() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitForeignKeyName() {
		return jaxbManyToManyElement.getForeignKey();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isCascadeDeleteEnabled() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUnique() {
		return jaxbManyToManyElement.isUnique();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getWhere() {
		return jaxbManyToManyElement.getWhere();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public FetchCharacteristics getFetchCharacteristics() {
		return fetchCharacteristics;
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
	public boolean isOrdered() {
		return StringHelper.isNotEmpty( getOrder() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getOrder() {
		return jaxbManyToManyElement.getOrderBy();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean createForeignKeyConstraint() {
		// TODO: Can HBM do something like JPA's @ForeignKey(NO_CONSTRAINT)?
		return true;
	}

}
