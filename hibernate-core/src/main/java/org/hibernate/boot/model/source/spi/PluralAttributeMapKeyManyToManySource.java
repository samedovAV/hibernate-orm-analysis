/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Additional source information for {@code <map-key-many-to-many/>} and
 * {@code <index-many-to-many/>}.
 *
 * @author Steve Ebersole
 */
public interface PluralAttributeMapKeyManyToManySource
		extends PluralAttributeMapKeySource, RelationalValueSourceContainer {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getReferencedEntityName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitForeignKeyName();
}
