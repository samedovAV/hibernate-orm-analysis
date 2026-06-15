/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.identity;

import org.hibernate.MappingException;
import org.hibernate.generator.EventType;
import org.hibernate.id.insert.GetGeneratedKeysDelegate;
import org.hibernate.persister.entity.EntityPersister;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Andrea Boriero
 */
public class IdentityColumnSupportImpl implements IdentityColumnSupport {

	public static final IdentityColumnSupportImpl INSTANCE = new IdentityColumnSupportImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsIdentityColumns() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean supportsInsertSelectIdentity() {
		return false;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean hasDataTypeInIdentityColumn() {
		return true;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String appendIdentitySelectToInsert(String identityColumnName, String insertString) {
		return insertString;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentitySelectString(String table, String column, int type) throws MappingException {
		throw new MappingException( getClass().getName() + " does not support identity key generation" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentityColumnString(int type) throws MappingException {
		throw new MappingException( getClass().getName() + " does not support identity key generation" );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getIdentityInsertString() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public GetGeneratedKeysDelegate buildGetGeneratedKeysDelegate(EntityPersister persister) {
		return new GetGeneratedKeysDelegate( persister, true, EventType.INSERT );
	}
}
