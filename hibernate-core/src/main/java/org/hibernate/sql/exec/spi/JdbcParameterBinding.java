/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import org.hibernate.metamodel.mapping.JdbcMapping;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JdbcParameterBinding {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcMapping getBindType();
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Object getBindValue();
}
