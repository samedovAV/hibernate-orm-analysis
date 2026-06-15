/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;
import java.util.Set;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmBasicAttributeType;
import org.hibernate.boot.model.source.spi.SizeSource;

import static org.hibernate.internal.util.StringHelper.splitAtCommas;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ColumnAndFormulaSource implementation handling basic attribute mappings.
 *
 * @author Steve Ebersole
 */
public class BasicAttributeColumnsAndFormulasSource
		extends RelationalValueSourceHelper.AbstractColumnsAndFormulasSource
		implements RelationalValueSourceHelper.ColumnsAndFormulasSource {
	private final JaxbHbmBasicAttributeType basicAttributeMapping;

	public BasicAttributeColumnsAndFormulasSource(JaxbHbmBasicAttributeType basicAttributeMapping) {
		this.basicAttributeMapping = basicAttributeMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.PROPERTY;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSourceName() {
		return basicAttributeMapping.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getFormulaAttribute() {
		return basicAttributeMapping.getFormulaAttribute();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getColumnAttribute() {
		return basicAttributeMapping.getColumnAttribute();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List getColumnOrFormulaElements() {
		return basicAttributeMapping.getColumnOrFormula();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public SizeSource getSizeSource() {
		return Helper.interpretSizeSource(
				basicAttributeMapping.getLength(),
				basicAttributeMapping.getScale(),
				basicAttributeMapping.getPrecision()
		);
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isNullable() {
		return basicAttributeMapping.isNotNull() == null
				? null
				: !basicAttributeMapping.isNotNull();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getIndexConstraintNames() {
		return Set.of( splitAtCommas( basicAttributeMapping.getIndex() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUnique() {
		return basicAttributeMapping.isUnique();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getUniqueKeyConstraintNames() {
		return Set.of( splitAtCommas( basicAttributeMapping.getUniqueKey() ) );
	}
}
