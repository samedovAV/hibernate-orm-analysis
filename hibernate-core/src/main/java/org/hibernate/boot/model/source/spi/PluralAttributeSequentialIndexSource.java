/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Defines the index of a persistent list/array
 *
 * @author Gail Badner
 * @author Steve Ebersole
 */
public interface PluralAttributeSequentialIndexSource extends PluralAttributeIndexSource, RelationalValueSourceContainer {
	/**
	 * Hibernate allows specifying the base value to use when storing the index
	 * to the database.  This reports that "offset" value.
	 *
	 * @return The index base value.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getBase();
}
