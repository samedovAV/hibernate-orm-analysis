/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.dialect.sequence;

import org.hibernate.MappingException;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Sequence support for {@link org.hibernate.dialect.SQLServerDialect}.
 *
 * @author Christian Beikov
 */
public class SQLServerSequenceSupport extends ANSISequenceSupport {

	public static final SequenceSupport INSTANCE = new SQLServerSequenceSupport();

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public String getSequencePreviousValString(String sequenceName) throws MappingException {
		return "select convert(varchar(200),current_value) from sys.sequences where name='" + sequenceName + "'";
	}
}
