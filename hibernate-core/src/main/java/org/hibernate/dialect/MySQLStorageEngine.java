/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * This interface defines how various MySQL storage engines behave in regard to Hibernate functionality.
 *
 * @author Vlad Mihalcea
 */
public interface MySQLStorageEngine {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean supportsCascadeDelete();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getTableTypeString(String engineKeyword);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean hasSelfReferentialForeignKeyBug();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean dropConstraints();
}
