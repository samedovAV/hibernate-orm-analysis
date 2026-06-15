/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;

import org.hibernate.boot.model.source.spi.AttributePath;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Context for determining the implicit name for an ANY mapping's key
 * column.  Historically the ANY key column name had to be specified.
 *
 * @author Steve Ebersole
 */
public interface ImplicitAnyKeyColumnNameSource extends ImplicitNameSource {
	/**
	 * Access to the AttributePath of the ANY mapping
	 *
	 * @return The AttributePath of the ANY mapping
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	AttributePath getAttributePath();
}
