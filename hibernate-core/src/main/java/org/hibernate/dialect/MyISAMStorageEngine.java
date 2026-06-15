/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Represents the MyISAM storage engine.
 *
 * @author Vlad Mihalcea
 */
public class MyISAMStorageEngine implements MySQLStorageEngine {

	public static final MySQLStorageEngine INSTANCE = new MyISAMStorageEngine();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsCascadeDelete() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getTableTypeString(String engineKeyword) {
		return String.format( " %s=MyISAM", engineKeyword );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasSelfReferentialForeignKeyBug() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean dropConstraints() {
		return false;
	}
}
