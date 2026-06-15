/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.sql.exec.spi;

import java.sql.CallableStatement;
import jakarta.persistence.ParameterMode;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.OutputableType;
import org.hibernate.sql.exec.internal.JdbcCallRefCursorExtractorImpl;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Steve Ebersole
 */
public interface JdbcCallParameterRegistration {

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	String getName();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	ParameterMode getParameterMode();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	void registerParameter(
			CallableStatement callableStatement,
			SharedSessionContractImplementor session);

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcParameterBinder getParameterBinder();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcCallParameterExtractor<?> getParameterExtractor();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	JdbcCallRefCursorExtractorImpl getRefCursorExtractor();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	OutputableType<?> getParameterType();
}
