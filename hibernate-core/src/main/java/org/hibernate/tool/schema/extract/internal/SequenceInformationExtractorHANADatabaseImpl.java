/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Vlad Mihalcea
 */
public class SequenceInformationExtractorHANADatabaseImpl extends SequenceInformationExtractorLegacyImpl {
	/**
	 * Singleton access
	 */
	public static final SequenceInformationExtractorHANADatabaseImpl INSTANCE = new SequenceInformationExtractorHANADatabaseImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceCatalogColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceSchemaColumn() {
		return "schema_name";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceStartValueColumn() {
		return "start_number";
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
	protected String sequenceIncrementColumn() {
		return "increment_by";
	}
}
