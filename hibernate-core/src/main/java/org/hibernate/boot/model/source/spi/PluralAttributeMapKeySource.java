/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes source information about the key of a persistent map.
 *
 * @author Steve Ebersole
 *
 * @see PluralAttributeMapKeyManyToManySource
 * @see PluralAttributeMapKeyManyToAnySource
 */
public interface PluralAttributeMapKeySource extends PluralAttributeIndexSource {
	enum Nature {
		BASIC,
		EMBEDDED,
		MANY_TO_MANY,
		ANY
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Nature getMapKeyNature();
}
