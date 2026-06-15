/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import java.util.Map;

import org.hibernate.Incubating;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Parameter object representing options for schema management tool execution
 *
 * @author Steve Ebersole
 */
@Incubating
public interface ExecutionOptions {
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	Map<String,Object> getConfigurationValues();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	boolean shouldManageNamespaces();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ExceptionHandler getExceptionHandler();
}
