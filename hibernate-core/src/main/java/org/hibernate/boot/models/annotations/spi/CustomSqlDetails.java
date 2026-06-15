/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.annotations.spi;

import java.lang.annotation.Annotation;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Commonality for annotations which define custom insert, update and delete SQL
 *
 * @author Steve Ebersole
 */
public interface CustomSqlDetails extends Annotation {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String sql();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void sql(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean callable();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void callable(boolean value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String table();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void table(String value);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	java.lang.Class<? extends org.hibernate.jdbc.Expectation> verify();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void verify(java.lang.Class<? extends org.hibernate.jdbc.Expectation> value);
}
