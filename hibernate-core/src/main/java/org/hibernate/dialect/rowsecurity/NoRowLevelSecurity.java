/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.rowsecurity;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * No-op row-level security support.
 *
 * @author Gavin King
 */
public class NoRowLevelSecurity implements RowLevelSecurity {
	public static final NoRowLevelSecurity INSTANCE = new NoRowLevelSecurity();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsRowLevelSecurity() {
		return false;
	}
}
