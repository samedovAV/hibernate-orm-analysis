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
public class SequenceNameExtractorImpl extends SequenceInformationExtractorLegacyImpl {
	/**
	 * Singleton access
	 */
	public static final SequenceNameExtractorImpl INSTANCE = new SequenceNameExtractorImpl();

	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String resultSetSequenceName(ResultSet resultSet) throws SQLException {
		return resultSet.getString( 1 );
	}

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
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMaxValueColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceIncrementColumn() {
		return null;
	}
}
