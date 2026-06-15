/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;

import org.hibernate.boot.model.source.internal.hbm.IndexedPluralAttributeSource;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface PluralAttributeSourceArray extends IndexedPluralAttributeSource {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getElementClass();
}
