/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIndexManyToManyType;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmMapKeyManyToManyType;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexNature;
import org.hibernate.boot.model.source.spi.PluralAttributeMapKeyManyToManySource;
import org.hibernate.boot.model.source.spi.RelationalValueSource;
import org.hibernate.internal.util.StringHelper;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public class PluralAttributeMapKeyManyToManySourceImpl
		implements PluralAttributeMapKeyManyToManySource {

	private final String referencedEntityName;
	private final String fkName;

	private final HibernateTypeSource hibernateTypeSource;
	private final List<RelationalValueSource> relationalValueSources;

	public PluralAttributeMapKeyManyToManySourceImpl(
			MappingDocument mappingDocument,
			PluralAttributeSourceMapImpl pluralAttributeSourceMap,
			final JaxbHbmMapKeyManyToManyType jaxbMapKeyManyToManyMapping) {
		this.referencedEntityName = StringHelper.isNotEmpty( jaxbMapKeyManyToManyMapping.getEntityName() )
				? jaxbMapKeyManyToManyMapping.getEntityName()
				: mappingDocument.qualifyClassName( jaxbMapKeyManyToManyMapping.getClazz() );
		this.fkName = jaxbMapKeyManyToManyMapping.getForeignKey();

		this.hibernateTypeSource = new HibernateTypeSourceImpl(
				jaxbMapKeyManyToManyMapping.getEntityName()
		);

		this.relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.MAP_KEY_MANY_TO_MANY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getFormulaAttribute() {
						return jaxbMapKeyManyToManyMapping.getFormulaAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbMapKeyManyToManyMapping.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbMapKeyManyToManyMapping.getColumnOrFormula();
					}
				}
		);
	}

	public PluralAttributeMapKeyManyToManySourceImpl(
			MappingDocument mappingDocument,
			PluralAttributeSourceMapImpl pluralAttributeSourceMap,
			final JaxbHbmIndexManyToManyType jaxbIndexManyToManyMapping) {
		this.referencedEntityName = StringHelper.isNotEmpty( jaxbIndexManyToManyMapping.getEntityName() )
				? jaxbIndexManyToManyMapping.getEntityName()
				: mappingDocument.qualifyClassName( jaxbIndexManyToManyMapping.getClazz() );
		this.fkName = jaxbIndexManyToManyMapping.getForeignKey();

		this.hibernateTypeSource = new HibernateTypeSourceImpl(
				jaxbIndexManyToManyMapping.getEntityName()
		);

		this.relationalValueSources = RelationalValueSourceHelper.buildValueSources(
				mappingDocument,
				null,
				new RelationalValueSourceHelper.AbstractColumnsAndFormulasSource() {
					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public XmlElementMetadata getSourceType() {
						return XmlElementMetadata.INDEX_MANY_TO_MANY;
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public String getSourceName() {
						return null;
					}

					@Override
					@Prove(complexity = Complexity.O_N, n = "", count = {})
					public String getColumnAttribute() {
						return jaxbIndexManyToManyMapping.getColumnAttribute();
					}

					@Override
					@Prove(complexity = Complexity.O_1, n = "", count = {})
					public List getColumnOrFormulaElements() {
						return jaxbIndexManyToManyMapping.getColumn();
					}
				}
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getReferencedEntityName() {
		return referencedEntityName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getExplicitForeignKeyName() {
		return fkName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public PluralAttributeIndexNature getNature() {
		return PluralAttributeIndexNature.MANY_TO_MANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public HibernateTypeSource getTypeInformation() {
		return hibernateTypeSource;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getXmlNodeName() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Nature getMapKeyNature() {
		return Nature.MANY_TO_MANY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<RelationalValueSource> getRelationalValueSources() {
		return relationalValueSources;
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
}
