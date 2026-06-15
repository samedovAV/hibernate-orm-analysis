/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;

import org.hibernate.id.insert.GetGeneratedKeysDelegate;
import org.hibernate.id.insert.SybaseJConnGetGeneratedKeysDelegate;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SybaseJconnIdentityColumnSupport extends AbstractTransactSQLIdentityColumnSupport {
	public static final SybaseJconnIdentityColumnSupport INSTANCE = new SybaseJconnIdentityColumnSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GetGeneratedKeysDelegate buildGetGeneratedKeysDelegate(EntityPersister persister) {
		return new SybaseJConnGetGeneratedKeysDelegate( persister );
	}
}
