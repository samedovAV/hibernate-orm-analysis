/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Contract for hiding the differences between a passed {@code Writer},
 * {@code File} or {@code URL} in terms of how we write output scripts.
 *
 * @author Steve Ebersole
 */
public interface ScriptTargetOutput {
	/**
	 * Prepare the script target to {@linkplain #accept(String) accept} commands
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void prepare();

	/**
	 * Accept the given command and write it to the abstracted script
	 *
	 * @param command The command
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void accept(String command);

	/**
	 * Release this output
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void release();
}
