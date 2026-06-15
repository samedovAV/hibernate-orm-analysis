/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tool.schema.extract.internal;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;


/**
 * @author Steve Ebersole
 */
public class SequenceInformationExtractorH2DatabaseImpl extends SequenceInformationExtractorLegacyImpl {
	/**
	 * Singleton access
	 */
	public static final SequenceInformationExtractorH2DatabaseImpl INSTANCE = new SequenceInformationExtractorH2DatabaseImpl();

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
}
