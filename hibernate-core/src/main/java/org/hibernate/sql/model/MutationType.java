/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.model;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * The type of mutation
 *
 * @author Steve Ebersole
 */
public enum MutationType {
	INSERT( true ),
	UPDATE( true ),
	DELETE( false );

	private final boolean canSkipTables;

	MutationType(boolean canSkipTables) {
		this.canSkipTables = canSkipTables;
	}

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean canSkipTables() {
		return canSkipTables;
	}
}
