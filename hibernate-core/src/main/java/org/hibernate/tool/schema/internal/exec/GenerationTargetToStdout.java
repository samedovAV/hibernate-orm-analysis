/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import org.hibernate.internal.build.AllowSysOut;
import org.hibernate.tool.schema.spi.GenerationTarget;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * A {@link GenerationTarget} that writed DDL to {@link System#out}.
 *
 * @author Steve Ebersole
 */
public class GenerationTargetToStdout implements GenerationTarget {
	private final String delimiter;

	public GenerationTargetToStdout(String delimiter) {
		this.delimiter = delimiter;
	}

	public GenerationTargetToStdout() {
		this ( null );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void prepare() {
		// nothing to do
	}

	@Override
	@AllowSysOut
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void accept(String command) {
		System.out.println( delimiter == null ? command : command + delimiter );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public void release() {
	}

}
