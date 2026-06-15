/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Represents the InnoDB storage engine.
 *
 * @author Vlad Mihalcea
 */
public class InnoDBStorageEngine implements MySQLStorageEngine {

	public static final MySQLStorageEngine INSTANCE = new InnoDBStorageEngine();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsCascadeDelete() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableTypeString(String engineKeyword) {
		return String.format( " %s=InnoDB", engineKeyword );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasSelfReferentialForeignKeyBug() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean dropConstraints() {
		return true;
	}
}
