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
public class DB2zIdentityColumnSupport extends DB2IdentityColumnSupport {

	public static final DB2zIdentityColumnSupport INSTANCE = new DB2zIdentityColumnSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentitySelectString(String table, String column, int type) {
		return "select identity_val_local() from sysibm.sysdummy1";
	}
}
