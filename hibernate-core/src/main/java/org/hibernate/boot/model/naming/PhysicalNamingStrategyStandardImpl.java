/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.model.naming;

import java.io.Serializable;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Standard implementation of the {@link PhysicalNamingStrategy} contract. This is a trivial implementation
 * where each physical name is taken to be exactly identical to the corresponding logical name.
 *
 * @author Steve Ebersole
 */
public class PhysicalNamingStrategyStandardImpl implements PhysicalNamingStrategy, Serializable {
	/**
	 * Singleton access
	 */
	public static final PhysicalNamingStrategyStandardImpl INSTANCE = new PhysicalNamingStrategyStandardImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalCatalogName(Identifier logicalName, JdbcEnvironment context) {
		return logicalName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment context) {
		return logicalName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
		return logicalName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalSequenceName(Identifier logicalName, JdbcEnvironment context) {
		return logicalName;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment context) {
		return logicalName;
	}
}
