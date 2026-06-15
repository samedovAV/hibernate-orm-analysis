/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import org.hibernate.tool.schema.spi.GenerationTarget;
import org.hibernate.tool.schema.spi.SchemaManagementException;
import org.hibernate.tool.schema.spi.ScriptTargetOutput;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link GenerationTarget} that writes DDL to scripts.
 *
 * @author Steve Ebersole
 */
public class GenerationTargetToScript implements GenerationTarget {

	private final ScriptTargetOutput scriptTarget;
	private final String delimiter;

	public GenerationTargetToScript(
			ScriptTargetOutput scriptTarget,
			String delimiter) {
		if ( scriptTarget == null ) {
			throw new SchemaManagementException( "ScriptTargetOutput cannot be null" );
		}
		this.scriptTarget = scriptTarget;
		this.delimiter = delimiter;
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void prepare() {
		scriptTarget.prepare();
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void accept(String command) {
		scriptTarget.accept( delimiter == null ? command : command + delimiter );
	}

	@Override
	@Prove(complexity = Complexity.O_N, n = "", count = {})
	public void release() {
		scriptTarget.release();
	}

}
