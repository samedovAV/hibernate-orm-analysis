/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.procedure.internal;

import org.hibernate.procedure.UpdateCountOutput;
import com.samedov.annotation.Prove;
import com.samedov.annotation.Complexity;

/**
 * Implementation of UpdateCountOutput
 *
 * @author Steve Ebersole
 */
public class UpdateCountOutputImpl implements UpdateCountOutput {
	private final int updateCount;

	public UpdateCountOutputImpl(int updateCount) {
		this.updateCount = updateCount;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public int getUpdateCount() {
		return updateCount;
	}

	@Override
	@Prove(complexity = Complexity.O_1, n = "", count = {})
	public boolean isResultSet() {
		return false;
	}
}
