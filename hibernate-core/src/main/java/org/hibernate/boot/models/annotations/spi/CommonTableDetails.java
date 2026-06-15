/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Information which is common across all table annotations
 *
 * @author Steve Ebersole
 */
public interface CommonTableDetails
		extends DatabaseObjectDetails, UniqueConstraintCollector, IndexCollector, Commentable, Optionable {
	/**
	 * The table name
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String name();

	/**
	 * Setter for {@linkplain #name()}
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void name(String name);
}
