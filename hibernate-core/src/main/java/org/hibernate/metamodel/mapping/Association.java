/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.metamodel.mapping;

import org.hibernate.sql.results.graph.Fetchable;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for an association, mainly details relative to the foreign-key
 *
 * @author Steve Ebersole
 */
public interface Association extends Fetchable {
	/**
	 * The descriptor, allowing access to column(s), etc
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDescriptor getForeignKeyDescriptor();

	/**
	 * Indicates which "side" of the foreign-key this association describes
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ForeignKeyDescriptor.Nature getSideNature();
}
