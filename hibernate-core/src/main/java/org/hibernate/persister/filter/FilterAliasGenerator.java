/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.persister.filter;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 *
 * @author Rob Worsnop
 *
 */
public interface FilterAliasGenerator {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getAlias(String table);
}
