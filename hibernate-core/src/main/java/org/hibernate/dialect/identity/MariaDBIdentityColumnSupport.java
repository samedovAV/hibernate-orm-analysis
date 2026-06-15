/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Marco Belladelli
 */
public class MariaDBIdentityColumnSupport extends MySQLIdentityColumnSupport {
	public static final MariaDBIdentityColumnSupport INSTANCE = new MariaDBIdentityColumnSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String appendIdentitySelectToInsert(String identityColumnName, String insertString) {
		return insertString + " returning " + identityColumnName;
	}
}
