/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.jaxb.hbm.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Adaptive implementation of the {@link PluralAttributeInfo} for {@code <idbag/>} mappings which
 * do not support all the configuration available on other collection mappings.
 *
 * @author Steve Ebersole
 */
public abstract class PluralAttributeInfoIdBagAdapter
		extends JaxbHbmToolingHintContainer
		implements PluralAttributeInfo {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public JaxbHbmOneToManyCollectionElementType getOneToMany() {
		// idbag collections cannot contain 1-m mappings.
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isInverse() {
		// idbag collections own the association, and are therefore non-inverse
		return false;
	}
}
