/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.io.IOException;
import java.io.Writer;

import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.ScriptTargetOutput;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public abstract class AbstractScriptTargetOutput implements ScriptTargetOutput {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected abstract Writer writer();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare() {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(String command) {
		try {
			final var writer = writer();
			writer.write( command );
			writer.write( System.lineSeparator() );
			writer.flush();
		}
		catch (IOException e) {
			throw new CommandAcceptanceException( "Could not write \"" + command + "\" to target script file", e );
		}
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
	}
}
