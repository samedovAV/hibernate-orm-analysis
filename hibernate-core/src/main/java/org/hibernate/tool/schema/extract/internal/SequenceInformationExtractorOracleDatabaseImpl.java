/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * @author Vlad Mihalcea
 */
public class SequenceInformationExtractorOracleDatabaseImpl extends SequenceInformationExtractorLegacyImpl {
	/**
	 * Singleton access
	 */
	public static final SequenceInformationExtractorOracleDatabaseImpl INSTANCE = new SequenceInformationExtractorOracleDatabaseImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceCatalogColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceSchemaColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceStartValueColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMinValueColumn() {
		return "min_value";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMaxValueColumn() {
		return "max_value";
	}


	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetIncrementValue(ResultSet resultSet) throws SQLException {
		return resultSet.getBigDecimal( sequenceIncrementColumn() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetMinValue(ResultSet resultSet) throws SQLException {
		return resultSet.getBigDecimal( sequenceMinValueColumn() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected Number resultSetMaxValue(ResultSet resultSet) throws SQLException {
		return resultSet.getBigDecimal( sequenceMaxValueColumn() );
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceIncrementColumn() {
		return "increment_by";
	}
}
