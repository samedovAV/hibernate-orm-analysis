/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.internal.exec;

import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * Used in cases where a specified source cannot be found
 *
 * @author Steve Ebersole
 */
public class ScriptSourceInputNonExistentImpl extends AbstractScriptSourceInput {
	/**
	 * Singleton access
	 */
	public static final ScriptSourceInputNonExistentImpl INSTANCE = new ScriptSourceInputNonExistentImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getScriptDescription() {
		return "[injected ScriptSourceInputNonExistentImpl script]";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Reader prepareReader() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected void releaseReader(Reader reader) {
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public List<String> extract(Function<Reader, List<String>> extractor) {
		return Collections.emptyList();
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean exists() {
		return false;
	}
}
