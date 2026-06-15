/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

public class SQLServer16SequenceSupport extends SQLServerSequenceSupport{
	public static final SequenceSupport INSTANCE = new SQLServer16SequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getDropSequenceString(String sequenceName) throws MappingException {
		return "drop sequence if exists " + sequenceName;
	}
}
