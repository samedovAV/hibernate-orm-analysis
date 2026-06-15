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
public class SequenceInformationExtractorDB2DatabaseImpl extends SequenceInformationExtractorLegacyImpl {
	/**
	 * Singleton access
	 */
	public static final SequenceInformationExtractorDB2DatabaseImpl INSTANCE = new SequenceInformationExtractorDB2DatabaseImpl();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceNameColumn() {
		return "seqname";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceCatalogColumn() {
		return null;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceSchemaColumn() {
		return "seqschema";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceStartValueColumn() {
		return "start";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMinValueColumn() {
		return "minvalue";
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	protected String sequenceMaxValueColumn() {
		return "maxvalue";
	}
}
