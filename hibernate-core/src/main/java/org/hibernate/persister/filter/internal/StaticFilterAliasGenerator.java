/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.filter.internal;


import org.hibernate.persister.filter.FilterAliasGenerator;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 * @author Rob Worsnop
 *
 */
public class StaticFilterAliasGenerator implements FilterAliasGenerator {

	private final String alias;

	public StaticFilterAliasGenerator(String alias) {
		this.alias = alias;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getAlias(String table) {
		return alias;
	}

}
