/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.spi;

import org.hibernate.tool.schema.SourceType;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Describes a source for schema create, drop and migrate actions.
 * <p>
 * If {@link #getSourceType} indicates that a script should be a source, then
 * {@link #getScriptSourceInput} identifies the script.
 * <p>
 * The purpose here is also to allow other back-ends (OGM) by simply describing
 * where to find sources rather than defining the sources themselves.  The reason
 * being that ultimately the Java type representing a "DDL command" might be different;
 * e.g., String for JDBC.
 *
 * @author Steve Ebersole
 */
public interface SourceDescriptor {
	/**
	 * The indicated source type for this target type.
	 *
	 * @return The source type
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	SourceType getSourceType();

	/**
	 * If {@link #getSourceType()} indicates scripts are involved, returns
	 * a representation of the script file to read.  Otherwise, returns {@code null}.
	 * <p>
	 * While it is ultimately up to the actual tooling provider, it is generally an error
	 * for {@link #getSourceType()} to indicate that scripts are involved and for this
	 * method to return {@code null}.
	 *
	 * @return The script file to read.
	 */
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ScriptSourceInput getScriptSourceInput();
}
