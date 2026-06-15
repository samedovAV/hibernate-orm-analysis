/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Andrea Boriero
 */
public class MySQLIdentityColumnSupport extends IdentityColumnSupportImpl {

	public static final MySQLIdentityColumnSupport INSTANCE = new MySQLIdentityColumnSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsIdentityColumns() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentitySelectString(String table, String column, int type) {
		return "select last_insert_id()";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentityColumnString(int type) {
		//starts with 1, implicitly
		return "not null auto_increment";
	}
}
