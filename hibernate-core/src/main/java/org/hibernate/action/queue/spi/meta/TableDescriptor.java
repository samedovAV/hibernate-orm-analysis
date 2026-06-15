/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.action.queue.spi.meta;

import org.hibernate.Incubating;
import org.hibernate.sql.model.TableMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/// Details about a table in the domain model, used to creation mutation
/// operations in the graph-based ActionQueue implementation.
///
/// @author Steve Ebersole
/// @since 8.0
@Incubating
public interface TableDescriptor {
	/// The table's name.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String name();

	/// Whether the table is considered optional in relation to its group of tables.
	///
	/// @implNote Effectively means a [jakarta.persistence.SecondaryTable] mapped as
	/// [org.hibernate.annotations.SecondaryRow#optional()].
	///
	/// @see #getRelativePosition()
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isOptional();

	/// Details about the key for this table.
	///
	/// @todo Currently for collections this is the foreign-key, but imo this should always be the primary key
	/// 	and collection table descriptor should just have separate foreignKeyDescriptor attribute].
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableKeyDescriptor keyDescriptor();

	/// Whether this table has foreign-keys which refer back to the same table.
	///
	/// @implNote E.g., an `employee` table which has a `manager_fk` key that targets back at `employee`.`
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isSelfReferential();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasUniqueConstraints();

	/// Whether cascade deletion is defined on the underlying database table.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean cascadeDeleteEnabled();

	/// Details about insertions into this table.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMapping.MutationDetails insertDetails();

	/// Details about updates to this table.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMapping.MutationDetails updateDetails();

	/// Details about deletions to this table.
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	TableMapping.MutationDetails deleteDetails();

	/// This table's relative position within its "table group".
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	int getRelativePosition();
}
