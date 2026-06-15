/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Describes a schema generation target
 *
 * @author Steve Ebersole
 */
public interface GenerationTarget {

	/**
	 * Prepare for accepting actions
	 *
	 * @throws SchemaManagementException If there is a problem preparing the target.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void prepare();

	/**
	 * Called just before a script is executed using one or more calls to {@link #accept(String)}.
	 * <p>
	 * May be used for logging in particular.
	 * @param scriptSource The source for the script that is about to be executed.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	default void beforeScript(ScriptSourceInput scriptSource) {
		// Defaults to no-op
	}

	/**
	 * Accept a command
	 *
	 * @param command The command
	 *
	 * @throws SchemaManagementException If there is a problem accepting the action.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(String command);

	/**
	 * Release this target, giving it a change to release its resources.
	 *
	 * @throws SchemaManagementException If there is a problem releasing the target.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();
}
