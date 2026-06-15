/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.internal.hbm;

import java.util.List;
import java.util.Set;

import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmManyToOneType;

import static org.hibernate.internal.util.StringHelper.splitAtCommas;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * ColumnAndFormulaSource implementation handling many-to-one attribute mappings.
 *
 * @author Steve Ebersole
 */
public class ManyToOneAttributeColumnsAndFormulasSource extends RelationalValueSourceHelper.AbstractColumnsAndFormulasSource {
	private final JaxbHbmManyToOneType manyToOneMapping;

	public ManyToOneAttributeColumnsAndFormulasSource(JaxbHbmManyToOneType manyToOneMapping) {
		this.manyToOneMapping = manyToOneMapping;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public XmlElementMetadata getSourceType() {
		return XmlElementMetadata.MANY_TO_ONE;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSourceName() {
		return manyToOneMapping.getName();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getFormulaAttribute() {
		return manyToOneMapping.getFormulaAttribute();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public String getColumnAttribute() {
		return manyToOneMapping.getColumnAttribute();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List getColumnOrFormulaElements() {
		return manyToOneMapping.getColumnOrFormula();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Boolean isNullable() {
		return manyToOneMapping.isNotNull() == null
				? null
				: !manyToOneMapping.isNotNull();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getIndexConstraintNames() {
		return Set.of( splitAtCommas( manyToOneMapping.getIndex() ) );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public boolean isUnique() {
		return manyToOneMapping.isUnique();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Set<String> getUniqueKeyConstraintNames() {
		return Set.of( splitAtCommas( manyToOneMapping.getUniqueKey() ) );
	}
}
