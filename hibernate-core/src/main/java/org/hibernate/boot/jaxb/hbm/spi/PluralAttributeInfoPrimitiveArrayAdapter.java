/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;

import java.util.Collections;
import java.util.List;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class PluralAttributeInfoPrimitiveArrayAdapter
		extends JaxbHbmToolingHintContainer
		implements PluralAttributeInfo {
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInverse() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmLazyWithExtraEnum getLazy() {
		return JaxbHbmLazyWithExtraEnum.FALSE;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmOneToManyCollectionElementType getOneToMany() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmCompositeCollectionElementType getCompositeElement() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmManyToManyCollectionElementType getManyToMany() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmManyToAnyCollectionElementType getManyToAny() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<JaxbHbmFilterType> getFilter() {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getCascade() {
		return null;
	}
}
