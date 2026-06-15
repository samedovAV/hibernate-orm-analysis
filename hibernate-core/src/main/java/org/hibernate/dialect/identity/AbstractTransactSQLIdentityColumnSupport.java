/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class AbstractTransactSQLIdentityColumnSupport extends IdentityColumnSupportImpl {

	public static final AbstractTransactSQLIdentityColumnSupport INSTANCE = new AbstractTransactSQLIdentityColumnSupport();
	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsIdentityColumns() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentityColumnString(int type) throws MappingException {
		//starts with 1, implicitly
		return "identity not null";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentitySelectString(String table, String column, int type) throws MappingException {
		return "select @@identity";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsInsertSelectIdentity() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String appendIdentitySelectToInsert(String identityColumnName, String insertString) {
		return insertString + "\nselect @@identity";
	}
}
