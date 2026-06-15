/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.source.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Additional contract for things which describe foreign keys.
 *
 * @author Steve Ebersole
 */
public interface ForeignKeyContributingSource {
	/**
	 * Retrieve the name of the foreign key as supplied by the user, or {@code null} if the user supplied none.
	 *
	 * @return The user supplied foreign key name.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getExplicitForeignKeyName();

	/**
	 * Primarily exists to support JPA's {@code @ForeignKey(NO_CONSTRAINT)}.
	 *
	 * @return {@code true} if the FK constraint should be created, {@code false} if not.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean createForeignKeyConstraint();

	/**
	 * Is "cascade delete" enabled for the foreign key? In other words, if a record in the parent (referenced)
	 * table is deleted, should the corresponding records in the child table automatically be deleted?
	 *
	 * @return {@code true}, if the cascade delete is enabled; {@code false}, otherwise.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean isCascadeDeleteEnabled();
}
